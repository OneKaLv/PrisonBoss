package ru.trycat.bosses.mobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.trycat.bosses.*;
import ru.trycat.bosses.EntityTypes;
import ru.trycat.bosses.bosses.Boss;
import ru.trycat.bosses.utils.StringUtils;

import java.util.List;
import java.util.Random;

public class EnderMiteMob extends EntityEndermite implements Mob{

    Spawner spawner;
    int delay;
    LivingEntity livingEntity;
    Random random;

    public EnderMiteMob(Spawner spawner) {
        super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = this.getMobRegenDelay();
        this.livingEntity = (LivingEntity)this.getBukkitEntity();
        this.random = new Random();
        final List goalB = (List) EntityTypes.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
        goalB.clear();
        final List goalC = (List)EntityTypes.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
        goalC.clear();
        final List targetB = (List)EntityTypes.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
        targetB.clear();
        final List targetC = (List)EntityTypes.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
        targetC.clear();
        this.goalSelector.a(0, (PathfinderGoal)new PathfinderGoalFloat((EntityInsentient)this));
        this.goalSelector.a(2, (PathfinderGoal)new PathfinderGoalMeleeAttack((EntityCreature)this, (Class)EntityHuman.class, 1.0, true));
        this.goalSelector.a(5, (PathfinderGoal)new PathfinderGoalMoveTowardsRestriction((EntityCreature)this, 1.0));
        this.goalSelector.a(7, (PathfinderGoal)new PathfinderGoalRandomStroll((EntityCreature)this, 1.0));
        this.goalSelector.a(8, (PathfinderGoal)new PathfinderGoalRandomLookaround((EntityInsentient)this));
        this.targetSelector.a(3, (PathfinderGoal)new PathfinderGoalHurtByTarget((EntityCreature)this, true, new Class[0]));
        this.targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityHuman.class, true, true));
        this.targetSelector.a(0, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.targetSelector.a(2, (PathfinderGoal)new PathfinderGoalNearestAttackableTarget((EntityCreature)this, (Class)EntityPlayer.class, true));
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)this.getMobHealth());
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(10);
        this.getAttributeInstance(GenericAttributes.c).setValue(100.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMobSpeed());
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue((double)this.getMobDamage());
        this.setHealth(this.getMobHealth());
        this.setCustomName(this.getMobName());
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        (this.spawner = spawner).register(this);
        this.persistent = true;
        this.expToDrop = 0;
        this.livingEntity.setRemoveWhenFarAway(false);
    }

    @Override
    public void dropDeathLoot(final boolean flag, final int i){
        int amount = random.nextInt(4);
        if (amount != 0){
            ItemStack itemStack = new ItemStack(Material.INK_SACK,amount, (short) 5);
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName("§aПлоть пиявки");
            itemStack.setItemMeta(itemMeta);


            Bukkit.getWorlds().get(0).dropItemNaturally(this.getBukkitEntity().getLocation(),itemStack);
        }
    }

    public void die() {
        if (this.killer instanceof EntityPlayer){
            double money = StringUtils.fixDouble(random.nextDouble());
            this.killer.getBukkitEntity().sendMessage("§a§lВам было начислено: " + money + "$");
            Bosses.getEconomy().depositPlayer(((EntityPlayer) this.killer).getBukkitEntity(), money);
        }
        this.spawner.iDead();
        super.die();
    }

    @Override
    public float getMobHealth() {
        return 25;
    }

    @Override
    public String getMobName() {
        return ChatColor.DARK_PURPLE +  "Пиявка";
    }

    @Override
    public int getMobDamage() {
        return 4;
    }

    @Override
    public int getMobRegen() {
        return 0;
    }

    @Override
    public int getMobRegenDelay() {
        return 0;
    }

    @Override
    public Spawner getSpawner() {
        return null;
    }

    @Override
    public void setSpawner(Spawner p0) {

    }

    @Override
    public double getMobSpeed() {
        return 0.35;
    }

    @Override
    public int getMobMoney() {
        return 1;
    }

    @Override
    public int getMobAdditionalMoney() {
        return 0;
    }

    @Override
    public int getMobKills() {
        return 0;
    }

    @Override
    public double getMobKillsChance() {
        return 0;
    }
}
