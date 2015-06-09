package com.seed;

import java.util.*;

/**
 * Created by Ian on 5/26/2015.
 */
public class SeedSettings {
   private static SeedSettings settings;
   private Map<PatchType, Seed> seedsMap;
   private FarmLocation location;
   private List<FarmLocation> validLocations;
   private int counter;

   private SeedSettings () {
      this.seedsMap = new HashMap<>();
      this.validLocations = Arrays.asList(FarmLocation.FALADOR, FarmLocation.CAMALOT,
              FarmLocation.ARDOUGNE);
      FarmLocation start = FarmLocation.FALADOR;
      for (FarmLocation loc : validLocations) {
         if (loc.getLoc().distance() < start.getLoc().distance()) {
            start = loc;
         }
      }
      this.location = start;
      this.counter = this.location.ordinal();
   }

   public FarmLocation next () {
      counter++;
      this.location = validLocations.get(counter % validLocations.size());
      return this.location;
   }

   public static SeedSettings getSettings () {
      return settings == null ? (settings = new SeedSettings()) : settings;
   }

   public SeedSettings seed (Seed... seeds) {
      for (Seed s : seeds) {
         seedsMap.put(s.getType(), s);
      }
      return this;
   }


   public FarmLocation getLocation () {
      return location;
   }

   public Seed getSeed (PatchType type) {
      return seedsMap.get(type);
   }

   public Collection<Seed> getSeeds () {
      return seedsMap.values();
   }
}
