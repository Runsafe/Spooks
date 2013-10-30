package no.runsafe.spooks;

import no.runsafe.framework.RunsafeConfigurablePlugin;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(ItemManager.class);
	}
}
