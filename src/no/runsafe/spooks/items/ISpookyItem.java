package no.runsafe.spooks.items;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;

public interface ISpookyItem
{
	public Item getItem();
	public String getName();
	public void onConsumed(IPlayer player);
}
