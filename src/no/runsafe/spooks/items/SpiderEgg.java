package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public class SpiderEgg implements ISpookyItem
{
	@Override
	public Item getItem()
	{
		return Item.Brewing.FermentedSpiderEye;
	}

	@Override
	public String getName()
	{
		return "Small Goo-Covered Spider Egg";
	}

	@Override
	public void onConsumed(RunsafePlayer player)
	{
		player.removeItem(getItem(), 1);
		RunsafeWorld playerWorld = player.getWorld();

		if (playerWorld != null)
		{
			playerWorld.spawn(player.getLocation(), LivingEntity.CaveSpider.getEntityType());
			player.sendMessage("Picking up the egg it pops and a 'small' spider drops out, eek!");
		}
	}
}
