package com.lensmods.lenssgs.core.entity;

import com.lensmods.lenssgs.core.data.MaterialStats;
import com.lensmods.lenssgs.core.datacomps.TraitLevel;
import com.lensmods.lenssgs.core.util.LenUtil;
import com.lensmods.lenssgs.core.weaponsystems.WeaponAmmoStats;
import com.lensmods.lenssgs.init.LenDamageTypes;
import com.lensmods.lenssgs.init.LenEnts;
import com.lensmods.lenssgs.init.LenSounds;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
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
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;


//Heavily Modified from MrCrazyfish gun Mod. Like, really heavily, but the base is still visible
public class GenericProjectile extends Entity implements IEntityWithComplexSpawn {
    private static final Predicate<Entity> PROJECTILE_TARGETS = input -> input != null && input.isPickable() && !input.isSpectator();
    private static final Predicate<BlockState> IGNORE_LEAVES = input -> input != null && input.getBlock() instanceof LeavesBlock;

    protected int OwnerID;
    protected LivingEntity Owner;
    protected float dmg = 0f;
    protected int pierce = 0;
    protected int life = 40;
    public float getPercentLifeLeft() {
        return ((float) tickCount / life);
    }
    protected double gravityMod = 0;
    protected float velMult = 1f ;
    protected List<TraitLevel> traits = new ArrayList<>();
    protected boolean secondLife = false;
    public boolean getSecondLife() {
        return secondLife;
    }
    private ItemStack visualItem = ItemStack.EMPTY;

    public GenericProjectile(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GenericProjectile(EntityType<? extends Entity> entityType, Level worldIn, LivingEntity shooter, ItemStack weapon)
    {
        this(entityType, worldIn);
        this.OwnerID = shooter.getId();
        this.Owner = shooter;

        Vec3 dir = this.getDirection(shooter, weapon);
        this.setDeltaMovement((dir.x *0.5f) * velMult, (dir.y*0.5f) * velMult, (dir.z*0.5f) * velMult);
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
    public void set_dmg(float newdmg) {
        this.dmg = newdmg;
    }
    public void set_pierce(int newval) {
        this.pierce = newval;
    }
    public void setGravityMod(double newval) {
        this.gravityMod = -newval;
    }
    public void setVelMult(float newval) {
        this.velMult = newval;
    }
    public void setTraits(List<TraitLevel> traits) { this.traits = traits;}
    public ItemStack getVisualItem() {
        return this.visualItem;
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

            if(block instanceof TargetBlock)
            {
                if(this.Owner instanceof ServerPlayer serverPlayer)
                {
                    serverPlayer.awardStat(Stats.TARGET_HIT);
                    CriteriaTriggers.TARGET_BLOCK_HIT.trigger(serverPlayer, this, blockHitResult.getLocation(),
                            state.getSignal(level(),pos,getDirection()));
                }
            }

            if(block instanceof BellBlock bell)
            {
                bell.attemptToRing(this.level(), pos, blockHitResult.getDirection());
            }
            this.pierce--;
            if(this.pierce <=0) {
                if(secondLife) {
                    this.onDoom();
                    this.remove(RemovalReason.KILLED);
                }
                if (this.isAlive() && !secondLife) {
                    this.onExpire();
                }
            }
            return;
        }

        if(result instanceof EntityHitResult entityHitResult)
        {
            Entity entity = entityHitResult.getEntity();
            if(entity.getId() == this.OwnerID || entity instanceof GenericProjectile)
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
        for(TraitLevel trait : traits) {
            switch (trait.trait()) {
                case MaterialStats.BLAZING: {
                    entity.igniteForSeconds(trait.level() * 5);
                    break;
                }
                case MaterialStats.SHOCKING: {
                    if(LenUtil.randBetween(0f,100f) < trait.level()* 5) {
                       LightningBolt bonk = new LightningBolt(EntityType.LIGHTNING_BOLT,entity.level());
                       bonk.setPos(hitVec);
                       bonk.setDamage(this.getDamage()*0.25f);
                       bonk.tick();
                    }
                    break;
                }
                case MaterialStats.TRACING: {
                    if (entity instanceof LivingEntity living) {
                        MobEffectInstance boi = new MobEffectInstance(MobEffects.GLOWING, 200 * trait.level(), 0);
                        living.addEffect(boi);
                    }
                    break;
                }
                case MaterialStats.BLINDING: {
                    if (entity instanceof LivingEntity living) {
                        MobEffectInstance boi = new MobEffectInstance(MobEffects.BLINDNESS, 200 * trait.level(), 0);
                        living.addEffect(boi);
                    }
                    break;
                }
                case MaterialStats.CONCUSSIVE: {
                    if (entity instanceof LivingEntity living) {
                        var vec = this.getDeltaMovement();
                        living.knockback(2d*trait.level(), -vec.x,-vec.y);
                    }
                    break;
                }
                case MaterialStats.LACERATING: {
                    if (entity instanceof LivingEntity living) {
                        MobEffectInstance boi = new MobEffectInstance(LenEnts.LACERATE_EFFECT, 50 * trait.level(), 0);
                        living.addEffect(boi);
                    }
                    break;
                }
                case MaterialStats.SHATTERING: {
                    if (entity instanceof LivingEntity living) {
                        MobEffectInstance boi = new MobEffectInstance(LenEnts.SHATTER_EFFECT, 100 * trait.level(), trait.level() - 1);
                        living.addEffect(boi);
                    }
                    break;
                }
            }
        }
        float damage = this.getDamage();
        DamageSource source = LenDamageTypes.Sources.projectile(this.level().registryAccess(), this, this.Owner);
        entity.hurt(source, damage);

        this.pierce--;
        if(this.pierce <=0) {
            if(secondLife) {
                this.onDoom();
                this.remove(RemovalReason.KILLED);
            }
            if (this.isAlive()&& !secondLife) {
                this.onExpire();
            }
        }
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.gravityMod = pCompound.getDouble("gravityMod");
        this.life = pCompound.getInt("lifetime");
        this.pierce = pCompound.getInt("pierce");

        List<TraitLevel> tstore = new ArrayList<>();
        for (int i = 0; i <pCompound.getInt("traitamt"); i++ ) {
            tstore.add(new TraitLevel(pCompound.getString("trait"+i),pCompound.getInt("level"+i)));
        }
        this.traits = tstore;
        this.secondLife = pCompound.getBoolean("second");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putDouble("gravityMod",this.gravityMod);
        pCompound.putInt("lifetime",this.life);
        pCompound.putInt("pierce",this.pierce);
        pCompound.putInt("traitamt",this.traits.isEmpty() ? 0:traits.size());
        for(int i = 0; i<traits.size();i++) {
            TraitLevel traitL = traits.get(i);
            pCompound.putString("trait"+i,traitL.trait());
            pCompound.putInt("level"+i,traitL.level());
        }
        pCompound.putBoolean("second",this.secondLife);
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        buffer.writeInt(this.OwnerID);
        buffer.writeDouble(this.gravityMod);
        buffer.writeInt(this.life);
        buffer.writeInt(this.pierce);
        buffer.writeInt(this.traits.isEmpty() ? 0 : traits.size());
        for(int i = 0; i<traits.size();i++) {
            TraitLevel traitL = traits.get(i);
            buffer.writeUtf(traitL.trait());
            buffer.writeInt(traitL.level());
        }
        buffer.writeBoolean(this.secondLife);
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        this.OwnerID = additionalData.readInt();
        this.gravityMod = additionalData.readDouble();
        this.life = additionalData.readInt();
        this.pierce = additionalData.readInt();
        List<TraitLevel> tstore = new ArrayList<>();
        for (int i = 0; i <additionalData.readInt(); i++ ) {
            tstore.add(new TraitLevel(additionalData.readUtf(),additionalData.readInt()));
        }
        this.traits = tstore;
        this.secondLife = additionalData.readBoolean();
    }

    private Vec3 getDirection(LivingEntity shooter, ItemStack weapon)
    {
        float inacc = WeaponAmmoStats.getAccuracy(weapon);
        if(inacc <= 0) {
            return this.getVectorFromRotation(shooter.getXRot(), shooter.getYRot());
        }
        return this.getVectorFromRotation(shooter.getXRot() + LenUtil.randBetween(-inacc,inacc),shooter.getYRot() + LenUtil.randBetween(-inacc,inacc));
    }
    @Override
    public void tick() {
        super.tick();
        this.updateHeading();
        for (int i =0; i < 4;i++) {
            double nextPosX = this.getX() + this.getDeltaMovement().x();
            double nextPosY = this.getY() + this.getDeltaMovement().y();
            double nextPosZ = this.getZ() + this.getDeltaMovement().z();
            this.setPos(nextPosX, nextPosY, nextPosZ);
            if (this.gravityMod != 0) {
                this.setDeltaMovement(this.getDeltaMovement().add(0, this.gravityMod, 0));
            }
            performHitCalcs();
        }
        if(this.tickCount >= this.life)
        {
            if(secondLife) {
                this.onDoom();
                this.remove(RemovalReason.KILLED);
            }
            if (this.isAlive() && !secondLife) {
                this.onExpire();
            }
        }
    }

    protected void performHitCalcs() {
        if (!this.level().isClientSide()) {
            if (!secondLife) {
                Vec3 startVec = this.position();
                Vec3 endVec = startVec.add(this.getDeltaMovement());
                HitResult result = rayTraceBlocks(this.level(), new ClipContext(startVec, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this), IGNORE_LEAVES);
                if (result.getType() != HitResult.Type.MISS) {
                    endVec = result.getLocation();
                }

                List<EntityResult> hitEntities = null;
                if (this.pierce == 0) {
                    EntityResult entityResult = this.findEntityOnPath(startVec, endVec);
                    if (entityResult != null) {
                        hitEntities = Collections.singletonList(entityResult);
                    }
                } else {
                    hitEntities = this.findEntitiesOnPath(startVec, endVec);
                }

                if (hitEntities != null && hitEntities.size() > 0) {
                    for (EntityResult entityResult : hitEntities) {
                        result = new EntityHitResult(entityResult.getEntity());
                        if (((EntityHitResult) result).getEntity() instanceof Player player) {
                            if (this.Owner instanceof Player && !((Player) this.Owner).canHarmPlayer(player)) {
                                result = null;
                            }
                        }
                        if (result != null) {
                            this.onHit(result, startVec, endVec);
                        }
                    }
                }else {
                    this.onHit(result, startVec, endVec);
                }
            }
            else if(!level().isClientSide) {
                double rad = 12;
                Vec3 start = new Vec3(position().x + rad,position().y + rad,position().z + rad);
                Vec3 end = new Vec3(position().x - rad,position().y - rad,position().z - rad);
                AABB succbox = new AABB(start,end);
                List<Entity> targets = level().getEntities(this,succbox,PROJECTILE_TARGETS);
                for (Entity ent : targets) {
                    if (ent == Owner) {continue;};
                    double delx = (this.position().x() - ent.position().x);
                    double dely = (this.position().y() - ent.position().y);
                    double delz = (this.position().z() - ent.position().z);
                    double dist = rad - distanceTo(ent);
                    ent.addDeltaMovement(new Vec3(delx*(dist)*0.00125d,dely*(dist)*0.00125d,delz*(dist)*0.00125d));
                }
            }
        }
    }

    protected void onExpire() {
        if(traits.stream().anyMatch(trait -> trait.trait().equals(MaterialStats.VOID_TOUCHED))) {
            this.secondLife = true;
            this.visualItem = new ItemStack(Items.ENDER_EYE,1);
            if(!level().isClientSide) {
                setPos(position().add(0f, 0.75f, 0f));
            }
            this.life = (100 + life);
            this.setDeltaMovement(new Vec3(0,0,0));
            this.setGravityMod(0d);
            playSound(LenSounds.VOID_BOOM.get(),1.75f,1f);
        }
        if (!secondLife) {
            onDoom();
            remove(RemovalReason.KILLED);
        }
    }
    protected void onDoom() {
        if(traits.stream().anyMatch(trait -> trait.trait().equals(MaterialStats.EXPLOSIVE))) {
            TraitLevel trait = traits.stream().filter(traitLevel -> traitLevel.trait().equals(MaterialStats.EXPLOSIVE)).findFirst().get();
            float rad = trait.level() * 1.25f;
            Vec3 start = new Vec3(position().x + rad,position().y + rad,position().z + rad);
            Vec3 end = new Vec3(position().x - rad,position().y - rad,position().z - rad);
            AABB boombox = new AABB(start,end);
            List<Entity> targets = level().getEntities(this,boombox,PROJECTILE_TARGETS);
            for (Entity hit : targets) {
                float damage =this.getDamage()* (0.1f * trait.level());
                DamageSource source = LenDamageTypes.Sources.projectile(this.level().registryAccess(), this, this.Owner);
                hit.hurt(source, damage);
                if(hit instanceof LivingEntity living) {
                    living.knockback(trait.level()*0.2f,-(position().x),-(position().y));
                }
            }
        }
        if(traits.stream().anyMatch(trait -> trait.trait().equals(MaterialStats.LINGERING))) {
            TraitLevel trait = traits.stream().filter(traitLevel -> traitLevel.trait().equals(MaterialStats.LINGERING)).findFirst().get();
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(),this.getX(),this.getY()+0.5f,this.getZ());
            cloud.setDuration(100 * trait.level());
            cloud.setOwner(this.Owner);
            cloud.setRadius(2 * trait.level());
            cloud.setPotionContents(new PotionContents(LenEnts.POTIONS.getRegistry().get().wrapAsHolder(LenEnts.LACERATE_POTION.get())));
            cloud.tick();
        }
        if(traits.stream().anyMatch(trait -> trait.trait().equals(MaterialStats.VOID_TOUCHED))) {
            TraitLevel trait = traits.stream().filter(traitLevel -> traitLevel.trait().equals(MaterialStats.VOID_TOUCHED)).findFirst().get();
            double rad = trait.level();
            Vec3 start = new Vec3(position().x + rad,position().y + rad,position().z + rad);
            Vec3 end = new Vec3(position().x - rad,position().y - rad,position().z - rad);
            AABB doombox = new AABB(start,end);
            List<Entity> targets = level().getEntities(this,doombox,PROJECTILE_TARGETS);
            for (Entity hit : targets) {
                float damage = this.getDamage();
                DamageSource source = LenDamageTypes.Sources.projectile(this.level().registryAccess(), this, this.Owner);
                hit.hurt(source, damage);
            }
        }
    }
    public float getDamage()
    {
        return Math.max(0F, this.dmg);
    }
    //Dont touch anything down below here.
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
