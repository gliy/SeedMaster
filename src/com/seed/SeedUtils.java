package com.seed;

import org.tbot.methods.Camera;
import org.tbot.methods.Npcs;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.util.Filter;
import org.tbot.wrappers.Identifiable;
import org.tbot.wrappers.NPC;

import java.util.*;

/**
 * Created by Ian on 5/28/2015.
 */
public class SeedUtils {
   private SeedUtils () {
   }


   public static <E extends Identifiable> List<E>
   filterById (Collection<E> items) {
      Map<Integer, E> map = new HashMap<>();
      for (E item : items) {
         if (!map.containsKey(item.getID())) {
            map.put(item.getID(), item);
         }
      }
      return new ArrayList<>(map.values());
   }

   public static <E> List<E> filter (E[] items, Filter<? super E>
           filter) {
      List<E> rtn = new ArrayList<>();
      for (E item : items) {
         rtn.add(item);
      }
      return filter(rtn, filter);

   }

   public static String enumToString(Enum<?> e)  {
         StringBuilder rtn = new StringBuilder();
         char[] c = e.name().toCharArray();
         boolean cap = true;

         for (char c2 : c) {
            if (c2 == '_') {
               cap = true;
            } else if (cap) {
               rtn.append(Character.toUpperCase(c2));
               cap = false;
            } else {
               rtn.append(c2);
            }

         }

         return rtn.toString();
   }

   public static <E> List<E> filter (Collection<? extends E> items, Filter<? super E>
           filter) {
      List<E> rtn = new ArrayList<>();
      for (E item : items) {
         if (filter.accept(item)) {
            rtn.add(item);
         }
      }
      return Collections.unmodifiableList(rtn);
   }

   public static void info (Object... o) {
      log(LogLevel.INFO, o);
   }

   public static void warn (Object... o) {
      log(LogLevel.WARN, o);
   }

   public static void log (LogLevel level, Object[] params) {
      System.out.println(String.format("[%s]: %s", level.toString(),
              Arrays.toString(params)));
   }


   public static NPC walkTo (String name) {
      final NPC npc = Npcs.getNearest(name);
      if (npc != null) {
         if (!npc.isOnScreen()) {
            Camera.turnTo(npc.getLocation(), 10);
         }
         if (!npc.isOnScreen()) {
            Walking.walkTileMM(npc.getLocation(), 20, 20);
            Time.sleepUntil(new Condition() {
               @Override
               public boolean check () {
                  return npc.isOnScreen();
               }
            }, 15000);
         }
      }
      return npc;
   }

   public static void error (Object... o) {
      log(LogLevel.ERROR, o);
   }


   public static enum LogLevel {
      DEBUG, INFO, WARN, ERROR
   }
}
