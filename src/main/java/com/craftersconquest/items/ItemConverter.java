package com.craftersconquest.items;

import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.math.BigInteger;

public class ItemConverter {

    public String toBase64(ItemStack item) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutput = new DataOutputStream(outputStream);

        NBTTagList nbtTagListItems = new NBTTagList();
        NBTTagCompound nbtTagCompoundItem = new NBTTagCompound();

        net.minecraft.server.v1_14_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        nmsItem.save(nbtTagCompoundItem);

        nbtTagListItems.add(nbtTagCompoundItem);

        NBTCompressedStreamTools.a(nbtTagCompoundItem, (DataOutput) dataOutput);

        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }

    public ItemStack fromBase64(String data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());

        NBTTagCompound nbtTagCompoundRoot = NBTCompressedStreamTools.a(new DataInputStream(inputStream));

        net.minecraft.server.v1_14_R1.ItemStack nmsItem = net.minecraft.server.v1_14_R1.ItemStack.a(nbtTagCompoundRoot);

        ItemStack item = (ItemStack) CraftItemStack.asBukkitCopy(nmsItem);

        return item;
    }
}
