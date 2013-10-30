package no.runsafe.spooks;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.spooks.items.Ectoplasm;
import no.runsafe.spooks.items.RottenFish;
import no.runsafe.spooks.items.SpiderEgg;
import no.runsafe.spooks.items.SugarApple;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(ItemManager.class);

		// Items
		addComponent(RottenFish.class);
		addComponent(Ectoplasm.class);
		addComponent(SpiderEgg.class);
		addComponent(SugarApple.class);
	}
}
