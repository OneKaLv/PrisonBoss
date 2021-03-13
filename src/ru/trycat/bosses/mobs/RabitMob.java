package ru.trycat.bosses.mobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.trycat.bosses.Bosses;
import ru.trycat.bosses.EntityTypes;
import ru.trycat.bosses.Spawner;
import ru.trycat.bosses.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RabitMob  extends EntityMonster implements Mob{


    Spawner spawner;
    int delay;
    LivingEntity livingEntity;
    Random random;

    public RabitMob(Spawner spawner) {
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
        int chance = random.nextInt(100);


        if (chance < 3){
            org.bukkit.inventory.ItemStack  magic_dust = new ItemStack(Material.BLAZE_POWDER);
            ItemMeta magiMeta = magic_dust.getItemMeta();
            magiMeta.setDisplayName("§aМагическая пыль");
            List<String> lore1 = new ArrayList<>();
            lore1.add(" ");
            lore1.add("§fС помощью этой пыли можно повысить шанс");
            lore1.add("§fна успешное улучшение перка на новый уровень");
            magic_dust.setItemMeta(magiMeta);

            Bukkit.getWorlds().get(0).dropItemNaturally(this.getBukkitEntity().getLocation(),magic_dust);
        }
    }

    public void die() {
        if (this.killer instanceof EntityPlayer){
            double money = StringUtils.fixDouble(Math.random() * 5);
            if (money < 0.4){
                money = 0.4;
            }
            ItemStack itemStack = new ItemStack(Material.ARROW, 1 + random.nextInt(2));
            Bukkit.getWorlds().get(0).dropItemNaturally(this.getBukkitEntity().getLocation(),itemStack);
            this.killer.getBukkitEntity().sendMessage("§a§lВам было начислено: " + money + "$");
            Bosses.getEconomy().depositPlayer(((EntityPlayer) this.killer).getBukkitEntity(), money);
        }
        this.spawner.iDead();
        super.die();
    }

    @Override
    public float getMobHealth() {
        return 50;
    }

    @Override
    public String getMobName() {
        return "§aЗаяц";
    }

    @Override
    public int getMobDamage() {
        return 6;
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
        return 0.4;
    }

    @Override
    public int getMobMoney() {
        return 0;
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
