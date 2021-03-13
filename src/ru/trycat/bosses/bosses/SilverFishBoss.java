package ru.trycat.bosses.bosses;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import ru.trycat.bosses.Bosses;
import ru.trycat.bosses.CustomHolo;
import ru.trycat.bosses.Spawner;
import ru.trycat.bosses.utils.PlayerUtils;
import ru.trycat.bosses.utils.Utils;

import java.util.*;

public class SilverFishBoss extends EntitySilverfish implements Boss {

    private Spawner spawner;
    private int delay;
    private int disappearanceres = 300;
    private int disappearancedelay;
    private final LivingEntity livingEntity;
    private final Random random;
    private Map<String, Integer> attackers;
    int totalDamage;

    public SilverFishBoss(Spawner spawner) {
        super(((CraftWorld)spawner.getSpawnLocation().getWorld()).getHandle());
        this.delay = this.getMobRegenDelay();
        this.disappearancedelay = disappearanceres;
        this.livingEntity = (LivingEntity)this.getBukkitEntity();
        this.random = new Random();
        final List goalB = (List) ru.trycat.bosses.EntityTypes.getPrivateField("b", PathfinderGoalSelector.class, this.goalSelector);
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
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)this.getMobHealth());
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(15);
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
        this.attackers = new HashMap<>();
        this.totalDamage = 0;
    }

    @Override
    public void die() {
        if (this.killer instanceof EntityPlayer) {
            HashMap<String, Double> percents = Utils.calculatePercents((HashMap<String, Integer>) this.attackers, this.totalDamage);
            for (final String key : percents.keySet()) {
                final int money = (int)(percents.get(key) * this.getMobMoney() / 100.0);
                Bosses.getEconomy().depositPlayer(key, (double)money);
                if (Bukkit.getPlayer(key) != null) {
                    Bukkit.getPlayer(key).sendMessage("§aВы убили босса и получили " + money +"$");
                }
            }
            Bukkit.broadcastMessage(this.getDeathMessage());
        }
        this.spawner.iDead();
        super.die();
    }

    @Override
    public boolean damageEntity(final DamageSource damagesource, final float f) {
        final Entity entity = damagesource.i();
        if (entity != null && !(entity instanceof EntityArrow) && !(entity instanceof EntityProjectile)) {
            if (this.getGoalTarget() != null && this.getGoalTarget().getBukkitEntity().getVehicle() != null) {
                this.getGoalTarget().getBukkitEntity().getVehicle().eject();
            }
            if (entity.getBukkitEntity() instanceof Player) {
                final Player p = (Player)entity.getBukkitEntity();
                if (!this.attackers.containsKey(p.getName())) {
                    this.attackers.put(p.getName(), (int)f);
                }
                else {
                    this.attackers.put(p.getName(), (int)(this.attackers.get(p.getName()) + f));
                }
                this.totalDamage += (int)f;
                PlayerUtils.sendActionBar(p, getMobName(), this.getHealth());
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 1));
            }
            return super.damageEntity(damagesource, f);
        }
        return false;
    }

    @Override
    public void m() {
        if (this.getGoalTarget() != null) {
            final boolean isSameWorld = this.getBukkitEntity().getLocation().getWorld() == this.getGoalTarget().getBukkitEntity().getLocation().getWorld();
            final double distance = isSameWorld ? this.getBukkitEntity().getLocation().distance(this.getGoalTarget().getBukkitEntity().getLocation()) : 32.0;
            if (this.getBukkitEntity().getLocation().getWorld().getName().equals(this.getGoalTarget().getBukkitEntity().getLocation().getWorld().getName()) && distance <= 20.0) {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(this.getMobSpeed());
            }
            else {
                this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
            }
        }
        else {
            this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.0);
        }
        if (this.getGoalTarget() != null && this.getBukkitEntity().getLocation().getWorld().getName().equals(this.getGoalTarget().getBukkitEntity().getLocation().getWorld().getName()) && this.spawner != null && this.getGoalTarget().getBukkitEntity().getLocation().distance(this.getBukkitEntity().getLocation()) > 60.0) {
            this.setGoalTarget((EntityLiving)null);
        }
        if (this.delay-- <= 0) {
            this.setCustomName(this.getMobName());
            this.delay = this.getMobRegenDelay();
            if (this.getHealth() < this.getMobHealth()) {
                this.heal((float)this.getMobRegen(), EntityRegainHealthEvent.RegainReason.REGEN);
            }
        }
        if (this.disappearancedelay-- <= 0){
            this.disappearancedelay = disappearanceres;
            this.getBukkitEntity().teleport(new Location(Bukkit.getWorlds().get(0),1923.5, 22, 2077.5));
            this.getBukkitEntity().getWorld().playSound(spawner.getSpawnLocation(), Sound.ENDERMAN_TELEPORT,0.3f,1);
            new BukkitRunnable() {
                @Override
                public void run() {
                    getBukkitEntity().teleport(spawner.getSpawnLocation());
                }
            }.runTaskLater(Bosses.instance,60);
        }
        super.m();
    }

    @Override
    public String getDeathMessage() {
        return "§4Королевская крыса §cбыла повержена! §aНападающие получили ценные награды!";
    }

    @Override
    public float getMobHealth() {
        return 250;
    }

    @Override
    public String getMobName() {
        return "§4Королевская крыса";
    }

    @Override
    public int getMobDamage() {
        return 8;
    }

    @Override
    public int getMobRegen() {
        return 2;
    }

    @Override
    public int getMobRegenDelay() {
        return 30;
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
        return 0.33;
    }

    @Override
    public int getMobMoney() {
        return 250;
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
