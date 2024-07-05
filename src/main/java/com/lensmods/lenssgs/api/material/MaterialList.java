package com.lensmods.lenssgs.api.material;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class MaterialList implements List<IMaterialInstance> {

    private final List<IMaterialInstance> list = new ArrayList<>();

    private  MaterialList(){}
    public static MaterialList empty() {
        return new MaterialList();
    }
    public static MaterialList of(Collection<? extends  IMaterialInstance> mats) {
        MaterialList yee = new MaterialList();
        yee.list.addAll(mats);
        return yee;
    }
    public static MaterialList of(IMaterialInstance... mats) {
        MaterialList yee = new MaterialList();
        Collections.addAll(yee.list,mats);
        return yee;
    }
    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @NotNull
    @Override
    public Iterator<IMaterialInstance> iterator() {
        return list.iterator();
    }

    @NotNull
    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @NotNull
    @Override
    public <T> T[] toArray(@NotNull T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(IMaterialInstance iMaterialInstance) {
        return list.add(iMaterialInstance);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends IMaterialInstance> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends IMaterialInstance> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public void clear() {
    list.clear();
    }

    @Override
    public IMaterialInstance get(int index) {
        return list.get(index);
    }

    @Override
    public IMaterialInstance set(int index, IMaterialInstance element) {
        return list.set(index,element);
    }

    @Override
    public void add(int index, IMaterialInstance element) {
        list.add(index,element);
    }

    @Override
    public IMaterialInstance remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<IMaterialInstance> listIterator() {
        return list.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<IMaterialInstance> listIterator(int index) {
        return list.listIterator(index);
    }

    @NotNull
    @Override
    public List<IMaterialInstance> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex,toIndex);
    }
}
