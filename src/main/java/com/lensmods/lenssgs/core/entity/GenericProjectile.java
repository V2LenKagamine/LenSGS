package com.lensmods.lenssgs.core.entity;

import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.init.LenDamageTypes;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.TargetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericProjectile extends Entity implements IEntityWithComplexSpawn {
    private static final Predicate<Entity> PROJECTILE_TARGETS = input -> input != null && input.isPickable() && !input.isSpectator();
    private static final Predicate<BlockState> IGNORE_LEAVES = input -> input != null && input.getBlock() instanceof LeavesBlock;

    protected int OwnerID;
    protected LivingEntity Owner;
    protected float bonus_dmg = 0f;

    public GenericProjectile(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GenericProjectile(EntityType<? extends Entity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon)
    {
        this(entityType, worldIn);
        this.OwnerID = shooter.getId();
        this.Owner = shooter;

        Vec3 dir = this.getDirection(shooter, weapon);
        this.setDeltaMovement(dir.x, dir.y, dir.z);
        this.updateHeading();

        double posX = shooter.xOld + (shooter.getX() - shooter.xOld) / 2.0;
        double posY = shooter.yOld + (shooter.getY() - shooter.yOld) / 2.0 + shooter.getEyeHeight();
        double posZ = shooter.zOld + (shooter.getZ() - shooter.zOld) / 2.0;
        this.setPos(posX, posY, posZ);
    }

    public void updateHeading()
    {
        double horizontalDistance = this.getDeltaMovement().horizontalDistance();
        this.setYRot((float) (Mth.atan2(this.getDeltaMovement().x(), this.getDeltaMovement().z()) * (180D / Math.PI)));
        this.setXRot((float) (Mth.atan2(this.getDeltaMovement().y(), horizontalDistance) * (180D / Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }
    private void onHit(HitResult result, Vec3 startVec, Vec3 endVec)
    {
        if(result instanceof BlockHitResult blockHitResult)
        {
            if(blockHitResult.getType() == HitResult.Type.MISS)
            {
                return;
            }

            Vec3 hitVec = result.getLocation();
            BlockPos pos = blockHitResult.getBlockPos();
            BlockState state = this.level().getBlockState(pos);
            Block block = state.getBlock();

            if(block instanceof TargetBlock targetBlock)
            {
                int power = LenUtil.updateTargetBlock(targetBlock, this.level(), state, blockHitResult, this);
                if(this.Owner instanceof ServerPlayer serverPlayer)
                {
                    serverPlayer.awardStat(Stats.TARGET_HIT);
                    CriteriaTriggers.TARGET_BLOCK_HIT.trigger(serverPlayer, this, blockHitResult.getLocation(), power);
                }
            }

            if(block instanceof BellBlock bell)
            {
                bell.attemptToRing(this.level(), pos, blockHitResult.getDirection());
            }

            return;
        }

        if(result instanceof EntityHitResult entityHitResult)
        {
            Entity entity = entityHitResult.getEntity();
            if(entity.getId() == this.OwnerID)
            {
                return;
            }

            if(this.Owner instanceof Player player)
            {
                if(entity.hasIndirectPassenger(player))
                {
                    return;
                }
            }
            this.onHitEntity(entity, result.getLocation(), startVec, endVec);
            entity.invulnerableTime = 0;
        }
    }

    protected void onHitEntity(Entity entity, Vec3 hitVec, Vec3 startVec, Vec3 endVec)
    {
        float damage = this.getDamage();
        DamageSource source = LenDamageTypes.Sources.projectile(this.level().registryAccess(), this, this.Owner);
        entity.hurt(source, damage);
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {}

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.OwnerID);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.OwnerID = additionalData.readInt();
    }

    private Vec3 getDirection(LivingEntity shooter, ItemStack weapon)
    {
            return this.getVectorFromRotation(shooter.getXRot(), shooter.getYRot());
    }

    public float getDamage()
    {
        return Math.max(0F, this.bonus_dmg);
    }
    private Vec3 getVectorFromRotation(float pitch, float yaw)
    {
        float f = Mth.cos(-yaw * 0.017453292F - (float) Math.PI);
        float f1 = Mth.sin(-yaw * 0.017453292F - (float) Math.PI);
        float f2 = -Mth.cos(-pitch * 0.017453292F);
        float f3 = Mth.sin(-pitch * 0.017453292F);
        return new Vec3((double) (f1 * f2), (double) f3, (double) (f * f2));
    }
    @Nullable
    protected EntityResult findEntityOnPath(Vec3 startVec, Vec3 endVec)
    {
        Vec3 hitVec = null;
        Entity hitEntity = null;
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), PROJECTILE_TARGETS);
        double closestDistance = Double.MAX_VALUE;
        for(Entity entity : entities)
        {
            if(!entity.equals(this.Owner))
            {
                EntityResult result = this.getHitResult(entity, startVec, endVec);
                if(result == null)
                    continue;
                Vec3 hitPos = result.getHitPos();
                double distanceToHit = startVec.distanceTo(hitPos);
                if(distanceToHit < closestDistance)
                {
                    hitVec = hitPos;
                    hitEntity = entity;
                    closestDistance = distanceToHit;
                }
            }
        }
        return hitEntity != null ? new EntityResult(hitEntity, hitVec) : null;
    }

    @Nullable
    protected List<EntityResult> findEntitiesOnPath(Vec3 startVec, Vec3 endVec)
    {
        List<EntityResult> hitEntities = new ArrayList<>();
        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0), PROJECTILE_TARGETS);
        for(Entity entity : entities)
        {
            if(!entity.equals(this.Owner))
            {
                EntityResult result = this.getHitResult(entity, startVec, endVec);
                if(result == null)
                    continue;
                hitEntities.add(result);
            }
        }
        return hitEntities;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    private EntityResult getHitResult(Entity entity, Vec3 startVec, Vec3 endVec)
    {
        double expandHeight = entity instanceof Player && !entity.isCrouching() ? 0.0625 : 0.0;
        AABB boundingBox = entity.getBoundingBox();
        boundingBox = boundingBox.expandTowards(0, expandHeight, 0);

        Vec3 hitPos = boundingBox.clip(startVec, endVec).orElse(null);
        Vec3 grownHitPos = boundingBox.inflate(0.3, 0, 0.3).clip(startVec, endVec).orElse(null);
        if(hitPos == null && grownHitPos != null)
        {
            HitResult raytraceresult = rayTraceBlocks(this.level(), new ClipContext(startVec, grownHitPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this), IGNORE_LEAVES);
            if(raytraceresult.getType() == HitResult.Type.BLOCK)
            {
                return null;
            }
            hitPos = grownHitPos;
        }
        if(hitPos == null)
        {
            return null;
        }

        return new EntityResult(entity, hitPos);
    }

    private static BlockHitResult rayTraceBlocks(Level world, ClipContext context, Predicate<BlockState> ignorePredicate)
    {
        return performRayTrace(context, (rayTraceContext, blockPos) -> {
            BlockState blockState = world.getBlockState(blockPos);
            if(ignorePredicate.test(blockState)) return null;
            FluidState fluidState = world.getFluidState(blockPos);
            Vec3 startVec = rayTraceContext.getFrom();
            Vec3 endVec = rayTraceContext.getTo();
            VoxelShape blockShape = rayTraceContext.getBlockShape(blockState, world, blockPos);
            BlockHitResult blockResult = world.clipWithInteractionOverride(startVec, endVec, blockPos, blockShape, blockState);
            VoxelShape fluidShape = rayTraceContext.getFluidShape(fluidState, world, blockPos);
            BlockHitResult fluidResult = fluidShape.clip(startVec, endVec, blockPos);
            double blockDistance = blockResult == null ? Double.MAX_VALUE : rayTraceContext.getFrom().distanceToSqr(blockResult.getLocation());
            double fluidDistance = fluidResult == null ? Double.MAX_VALUE : rayTraceContext.getFrom().distanceToSqr(fluidResult.getLocation());
            return blockDistance <= fluidDistance ? blockResult : fluidResult;
        }, (rayTraceContext) -> {
            Vec3 Vector3d = rayTraceContext.getFrom().subtract(rayTraceContext.getTo());
            return BlockHitResult.miss(rayTraceContext.getTo(), Direction.getNearest(Vector3d.x, Vector3d.y, Vector3d.z), BlockPos.containing(rayTraceContext.getTo()));
        });
    }

    private static <T> T performRayTrace(ClipContext context, BiFunction<ClipContext, BlockPos, T> hitFunction, Function<ClipContext, T> p_217300_2_)
    {
        Vec3 startVec = context.getFrom();
        Vec3 endVec = context.getTo();
        if(startVec.equals(endVec))
        {
            return p_217300_2_.apply(context);
        }
        else
        {
            double startX = Mth.lerp(-0.0000001, endVec.x, startVec.x);
            double startY = Mth.lerp(-0.0000001, endVec.y, startVec.y);
            double startZ = Mth.lerp(-0.0000001, endVec.z, startVec.z);
            double endX = Mth.lerp(-0.0000001, startVec.x, endVec.x);
            double endY = Mth.lerp(-0.0000001, startVec.y, endVec.y);
            double endZ = Mth.lerp(-0.0000001, startVec.z, endVec.z);
            int blockX = Mth.floor(endX);
            int blockY = Mth.floor(endY);
            int blockZ = Mth.floor(endZ);
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(blockX, blockY, blockZ);
            T t = hitFunction.apply(context, mutablePos);
            if(t != null)
            {
                return t;
            }

            double deltaX = startX - endX;
            double deltaY = startY - endY;
            double deltaZ = startZ - endZ;
            int signX = Mth.sign(deltaX);
            int signY = Mth.sign(deltaY);
            int signZ = Mth.sign(deltaZ);
            double d9 = signX == 0 ? Double.MAX_VALUE : (double) signX / deltaX;
            double d10 = signY == 0 ? Double.MAX_VALUE : (double) signY / deltaY;
            double d11 = signZ == 0 ? Double.MAX_VALUE : (double) signZ / deltaZ;
            double d12 = d9 * (signX > 0 ? 1.0D - Mth.frac(endX) : Mth.frac(endX));
            double d13 = d10 * (signY > 0 ? 1.0D - Mth.frac(endY) : Mth.frac(endY));
            double d14 = d11 * (signZ > 0 ? 1.0D - Mth.frac(endZ) : Mth.frac(endZ));

            while(d12 <= 1.0D || d13 <= 1.0D || d14 <= 1.0D)
            {
                if(d12 < d13)
                {
                    if(d12 < d14)
                    {
                        blockX += signX;
                        d12 += d9;
                    }
                    else
                    {
                        blockZ += signZ;
                        d14 += d11;
                    }
                }
                else if(d13 < d14)
                {
                    blockY += signY;
                    d13 += d10;
                }
                else
                {
                    blockZ += signZ;
                    d14 += d11;
                }

                T t1 = hitFunction.apply(context, mutablePos.set(blockX, blockY, blockZ));
                if(t1 != null)
                {
                    return t1;
                }
            }

            return p_217300_2_.apply(context);
        }
    }

    public static class EntityResult
    {
        private Entity entity;
        private Vec3 hitVec;

        public EntityResult(Entity entity, Vec3 hitVec)
        {
            this.entity = entity;
            this.hitVec = hitVec;
        }
        public Entity getEntity()
        {
            return this.entity;
        }
        public Vec3 getHitPos()
        {
            return this.hitVec;
        }
    }


}
