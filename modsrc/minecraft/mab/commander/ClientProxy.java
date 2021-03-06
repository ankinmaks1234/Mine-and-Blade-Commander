package mab.commander;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.registry.TickRegistry;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraftforge.client.MinecraftForgeClient;
import mab.commander.CommonProxy;
import mab.commander.MBCommander;
import mab.commander.block.TileEntityBanner;
import mab.commander.block.TileEntityBannerRenderer;
import mab.commander.gui.GUISpawn;
import mab.commander.gui.order.GUIOrderMenu;
import mab.commander.lang.LanguageHelper;
import mab.commander.npc.EntityMBUnit;
import mab.commander.npc.RendererMBKnight;
import mab.commander.npc.RendererMBUnit;
import mab.commander.npc.melee.EntityMBKnight;
import mab.commander.npc.melee.EntityMBMilitia;

public class ClientProxy extends CommonProxy{
	
	public static GUIOrderMenu orderMenu;
	
	public static List<EntityMBUnit> selectedUnits = new ArrayList<EntityMBUnit>();
	
	@Override
	public void resetSelectedUnits(){
		selectedUnits = new ArrayList<EntityMBUnit>();
	}
	
	

	@Override
	public List<EntityMBUnit> getSelectedUnits() {
		return selectedUnits;
	}



	@Override
	public void loadLanguages(){
		LanguageHelper.loadAllLanguages();
	}
	
	@Override
	public void registerRenderInformation(){
		MinecraftForgeClient.preloadTexture(MBCommander.ImageSheet);
		RenderingRegistry.instance().registerEntityRenderingHandler(EntityMBMilitia.class, new RendererMBUnit(new ModelBiped(.1F), 0));   
		RenderingRegistry.instance().registerEntityRenderingHandler(EntityMBKnight.class, new RendererMBKnight(new ModelBiped(.1F), 0));   
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBanner.class, new TileEntityBannerRenderer());
		
		KeyBindingRegistry.registerKeyBinding(new MBKeyHandeler());
		TickRegistry.registerTickHandler(new MBRnderHandeler(), Side.CLIENT);
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		switch(ID){
			case 0:
				TileEntity tile = world.getBlockTileEntity(x, y, z);
				if(tile != null && tile instanceof TileEntityBanner)
					return new GUISpawn((TileEntityBanner) tile, player);
				else
					return null;
			default:
				return null;
		}
	}
	
	
	
}
