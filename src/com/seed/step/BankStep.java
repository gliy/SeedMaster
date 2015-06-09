package com.seed.step;

import com.seed.FarmLocation;
import com.seed.Seed;
import com.seed.SeedSettings;
import com.seed.SeedUtils;
import com.seed.items.SeedItem;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.Bank;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.combat.magic.Rune;
import org.tbot.methods.tabs.Equipment;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.banks.WebBank;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ian on 5/28/2015.
 */
public class BankStep implements Step<FarmLocation> {


   @Override
   public StepResult execute (FarmLocation param) {
      boolean noBank = true;
      for (SeedItem si : SeedItem.values()) {
         if (si != SeedItem.BUCKET) {
            if (si.getInvCount() != si.getAmount()) {
               noBank = false;
               break;
            }
         }
      }
      if (noBank || 1==1) {
         SeedUtils.info("No need to bank!");
         return StepResult.create(Steps.TO_PATCH, SeedSettings.getSettings().getLocation());
      }
      final WebBank bank = param.getBank();
      if (!Bank.isOpen()) {
         if (!bank.getLocation().isOnScreen()) {
            Walking.findPath(bank.getLocation()).traverse();
            Time.sleepUntil(new Condition() {
               @Override
               public boolean check () {
                  return bank.getLocation().isOnScreen();
               }
            });
         }

         if (!Bank.isOpen()) {

            GameObject nearest = GameObjects.getNearest("Bank booth");
            if (nearest == null) {
               return StepResult.error("Script Errorr: Bank not found");
            }

            nearest.interact("Bank");

         }
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check () {
               return Bank.isOpen();
            }
         }, 10000);
      }
      if (!Bank.isOpen()) {
         return StepResult.create(Steps.ERROR, "Bank could not be opened");
      }
      if (SeedItem.BUCKET.get() != -1) {
         System.out.println("BUCKET");
         Bank.depositAll(SeedItem.BUCKET.get());
      }
      if (Inventory.contains("Weeds")) {
         Bank.depositAll("Weeds");
      }

      Bank.setNoted(false);
      if (Bank.getOpenTab() != 0) {
         Bank.openTab(0);
      }
      List<SeedItem> needed = new ArrayList<>();
      for (SeedItem si : SeedItem.values()) {
         if (si != SeedItem.BUCKET && si.getInvCount() < si.getAmount()) {
            if (si.getBankCount() < si.getAmount()) {
               StepResult.error(String.format("Bank only has %s of %s, but need %s",
                       si.getBankCount(), si.toString(), si.getAmount()));
            }
            int invCount = si.getInvCount();
            if (invCount < si.getAmount()) {
               needed.add(si);
            } else {
               SeedUtils.info("Inventory already contains %sx%s, Ignoring...", si, si.getAmount());
            }
         }
      }
      if (!needed.isEmpty()) {
         for (SeedItem si : needed) {
            if (!si.widthdraw()) {
               return StepResult.error(String.format("Withdrawl of %s failed", si));
            }
         }
      }

      String magic = "Magic secateurs";
      if (!Equipment.contains(magic) && Inventory.contains(magic)) {
         Inventory.getItemClosestToMouse(magic).click();
      }


      StepResult runeCheck = checkRunes();
      return runeCheck.getNextStep() == Steps.ERROR ? runeCheck : getSeeds();


   }

   private StepResult checkRunes () {
      Rune[] runes = new Rune[]{Rune.AIR_RUNE, Rune.LAW_RUNE, Rune.WATER_RUNE};
      for (Rune r : runes) {
         if ((Bank.getCount(r.getId()) + Inventory.getCount(r.getId())) < 100) {
            return StepResult.error(String.format("Not enough %s", r.toString()));
         } else if (Inventory.getCount(r.getId()) < 5) {
            Bank.withdraw(r.getId(), Math.min(Bank.getCount(r.getId()), 100));
         }
      }

      return StepResult.create(Steps.BANK);
   }


   public StepResult getSeeds () {
      Collection<Seed> seeds = SeedSettings.getSettings().getSeeds();
      for (Seed s : seeds) {
         String name = s.toString();
         if ((Inventory.getCount(name) + Bank.getCount(name)) < 50) {
            StepResult.error(String.format("Not enough %s", name));
         } else {
            if (Inventory.getCount(name) < 10) {
               Bank.withdraw(name, Math.min(Bank.getCount(name), 50));
            }
         }
      }
      Bank.close();
      return StepResult.create(Steps.TO_PATCH, SeedSettings.getSettings().getLocation());
   }
}
