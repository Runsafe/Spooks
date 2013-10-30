package no.runsafe.spooks;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.spooks.items.TestApple;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(ItemManager.class);

		// Items
		addComponent(TestApple.class);
	}
}
