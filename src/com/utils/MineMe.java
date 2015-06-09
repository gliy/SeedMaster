package com.utils;

import org.tbot.internal.AbstractScript;
import org.tbot.internal.Manifest;
import org.tbot.methods.*;
import org.tbot.methods.tabs.Inventory;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.NPC;
import org.tbot.wrappers.Tile;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Ian on 5/30/2015.
 */

@Manifest(name = "MINE")
public class MineMe extends AbstractScript {
   int count = 0;
   int next = 60+new Random().nextInt(50);
   @Override
   public int loop() {
      Time.sleepUntil(new Condition() {
         @Override
         public boolean check() {
            return !Players.getLocal().inCombat();
         }
      });
      count ++;
      if(count >= next) {
         next = 60+new Random().nextInt(50);
         count = 0;
         Time.sleep(30000,40000);
      }
      if(!Inventory.isOpen()) {
         Inventory.openTab();
      }
      if(Inventory.isFull()) {
         Tile loc = Players.getLocal().getLocation();
         final NPC mistag = Npcs.getNearest("Mistag");
         if(!mistag.isOnScreen()) {
            Walking.walkTileMM(mistag.getLocation());
            Time.sleepUntil(new Condition() {
               @Override
               public boolean check() {
                  return mistag.isOnScreen();
               }
            });
         }
         mistag.interact("Talk-to");

         Time.sleep(100);
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return NPCChat.isChatOpen();
            }
         }, 10000);
         Time.sleep(500,900);
         if(NPCChat.isChatOpen()) {

            NPCChat.clickContinue();
            Time.sleepUntil(
                    new Condition() {
                       @Override
                       public boolean check() {
                          return NPCChat.getOptionCount() > 1;
                       }
                    }
            ,5000);
            System.out.println(Arrays.toString(NPCChat.getOptions()));
            System.out.println(NPCChat.selectOption("ore"));
            Time.sleepUntil(
                    new Condition() {
                       @Override
                       public boolean check() {
                          return NPCChat.canContinue();
                       }
                    }
                    ,5000);
            NPCChat.clickContinue();
            Time.sleep(400, 600);
            Time.sleepUntil(
                    new Condition() {
                       @Override
                       public boolean check() {
                          return NPCChat.canContinue();
                       }
                    }
                    ,5000);
            NPCChat.clickContinue();

            Time.sleepUntil(
                    new Condition() {
                       @Override
                       public boolean check() {
                          return NPCChat.getOptionCount() > 1;
                       }
                    }
                    ,5000);
            NPCChat.selectOption("Okay");
            Time.sleepUntil(new Condition() {
               @Override
               public boolean check() {
                  return NPCChat.canContinue();
               }
            });
            Time.sleep(200,300);
            NPCChat.clickContinue();
            Time.sleep(200,300);

         }
         if(Inventory.isFull()) {
            Inventory.dropAll(440);
         }
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return !Inventory.contains(440);
            }
         },11000);
         Walking.walkTileMM(loc);
         Time.sleep(400, 900);
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return !Players.getLocal().isMoving();
            }
         });
      }
      final GameObject obj = GameObjects.getNearest(new Filter<GameObject>() {
         @Override
         public boolean accept(GameObject gameObject) {
            return (gameObject.getID() == 6944 || gameObject.getID() == 6943) &&
                    gameObject.distance(new Tile(3313,9621)) < 6;
         }
      });
      if(obj == null) {
         return 500;
      }
      final GameObject next = GameObjects.getNearest(new Filter<GameObject>() {
         @Override
         public boolean accept(GameObject gameObject) {
            return  (gameObject.getID() == 6944 || gameObject.getID() == 6943)
                    && gameObject.getUID() != obj.getUID();
         }
      });
//      if(next != null) {
//         Tile tile =  next.getModelLocation();
//
//         Mouse.move(tile.getX(), tile.getY(), 30, 50);
//         System.out.println("Moving mouse to " + next.getLocation());
//      }
      obj.interact("Mine");


      Time.sleepUntil(new Condition() {
         @Override
         public boolean check() {
            GameObject obj2 = GameObjects.getTopAt(obj.getLocation());
            return obj2 == null || obj2.getID() != obj.getID();
         }
      },1400);
      return 100;
   }
}
