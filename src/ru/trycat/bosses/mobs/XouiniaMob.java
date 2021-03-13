package ru.trycat.bosses.mobs;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.trycat.bosses.Bosses;
import ru.trycat.bosses.utils.PlayerUtils;
import ru.trycat.bosses.utils.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class XouiniaMob extends EntityMonster{

    int delay;
    LivingEntity livingEntity;
    Random random;

    public XouiniaMob(World w) {
        super(w);
        this.delay = this.getMobRegenDelay();
        this.livingEntity = (LivingEntity)this.getBukkitEntity();
        this.random = new Random();
        List goalB = (List)ru.trycat.bosses.EntityTypes.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
        goalB.clear();
        final List goalC = (List)ru.trycat.bosses.EntityTypes.getPrivateField("c", PathfinderGoalSelector.class, this.goalSelector);
        goalC.clear();
        final List targetB = (List)ru.trycat.bosses.EntityTypes.getPrivateField("b", PathfinderGoalSelector.class, this.targetSelector);
        targetB.clear();
        final List targetC = (List)ru.trycat.bosses.EntityTypes.getPrivateField("c", PathfinderGoalSelector.class, this.targetSelector);
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
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(15);
        this.getAttributeInstance(GenericAttributes.c).setValue(100.0);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.35);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue((double)this.getMobDamage());
        this.setHealth(this.getMobHealth());
        this.setCustomName(this.getMobName());
        this.setCustomNameVisible(true);
        this.canPickUpLoot = false;
        this.fireProof = true;
        this.persistent = true;
        this.expToDrop = 0;

    }

    @Override
    public boolean damageEntity(final DamageSource damagesource, final float f) {
        final Entity entity = damagesource.i();
        if (entity != null && !(entity instanceof EntityArrow) && !(entity instanceof EntityProjectile)) {
            if (this.getGoalTarget() != null && this.getGoalTarget().getBukkitEntity().getVehicle() != null) {
                this.getGoalTarget().getBukkitEntity().getVehicle().eject();
            }
            return super.damageEntity(damagesource, f);
        }
        return false;
    }



    @Override
    public void dropDeathLoot(final boolean flag, final int i){

    }

    public float getMobHealth() {
        return 40;
    }

    public String getMobName() {
        return "§9Начальник";
    }

    public int getMobDamage() {
        return 4;
    }

    public int getMobRegen() {
        return 0;
    }


    public int getMobRegenDelay() {
        return 0;
    }
}
