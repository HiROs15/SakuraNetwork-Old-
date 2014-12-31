package dev.hiros.Hub.HubBank.NMS;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R1.util.UnsafeList;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityVillager;
import net.minecraft.server.v1_8_R1.PathfinderGoalInteract;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class EntityBanker extends EntityVillager {
	public EntityBanker(World world, int i) {
		super(world);
		this.setProfession(i);
		
		try {
			Field b = PathfinderGoalSelector.class.getDeclaredField("b");
			b.setAccessible(true);
			
			b.set(this.goalSelector, new UnsafeList<PathfinderGoalSelector>());
		} catch(Exception e) {}
		
		this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
	}
	
	@Override
	public boolean a(EntityHuman entityHuman) {
		return true;
	}
	
	@Override
	public void b(EntityLiving entityliving) {
		return;
	}
	
	@Override
	protected void E() {
		return;
	}
}
