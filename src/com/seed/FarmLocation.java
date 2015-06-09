package com.seed;

import org.tbot.methods.tabs.Quests;
import org.tbot.methods.web.banks.WebBank;
import org.tbot.methods.web.banks.WebBanks;
import org.tbot.wrappers.Tile;

/**
 * Created by Ian on 5/28/2015.
 */
public enum FarmLocation {

   CAMALOT(new Tile(2811, 3465), WebBanks.CATHERBY_BANK,45),
   FALADOR(new Tile(3056, 3309), WebBanks.FALADOR_EAST_BANK,37),
   PORT_PHASMATYS(new Tile(-1, -1), WebBanks.PORT_PHASMATYS_BANK,-1),
   ARDOUGNE(new Tile(-1, -1), WebBanks.ARDOUGNE_NORTH_BANK,51);

   private Tile loc;
   private WebBank bank;


   FarmLocation (Tile loc, WebBank bank, int level) {
      this.loc = loc;
      this.bank = bank;

   }


   public Tile getLoc () {
      return loc;
   }

   public WebBank getBank () {
      return bank;
   }

   public boolean isEnabled () {
      //(Quests.isCompleted("Ghosts Ahoy") || this != PORT_PHASMATYS) &&

      return   this != PORT_PHASMATYS &&
              (Quests.isCompleted("Plague City") || this != ARDOUGNE);
   }



   @Override
   public String toString () {
     return SeedUtils.enumToString(this);
   }



}
