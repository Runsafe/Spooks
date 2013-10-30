package no.runsafe.spooks.items;

import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.player.RunsafePlayer;

public interface ISpookyItem
{
	public Item getItem();
	public String getName();
	public void onConsumed(RunsafePlayer player);
}
