package no.runsafe.spooks.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.entity.LivingEntity;

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
	public void onConsumed(IPlayer player)
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
