package com.seed;

/**
 * Created by Ian on 5/26/2015.
 */
public enum Seed {
   POTATO(1, PatchType.ALLOTMENT),
   ONION(5, PatchType.ALLOTMENT),
   CABBAGE(7, PatchType.ALLOTMENT),
   TOMATO(12, PatchType.ALLOTMENT),
   SWEETCORN(20, PatchType.ALLOTMENT),
   STRAWBERRY(31, PatchType.ALLOTMENT),
   WATERMELON(47, PatchType.ALLOTMENT),

   LIMPWURT(26, PatchType.FLOWER),

   GUAM(9, PatchType.HERB),
   MARRENTILL(14, PatchType.HERB),
   TARROMIN(19, PatchType.HERB),
   HARRALANDER(26, PatchType.HERB),
   RANARR(32, PatchType.HERB),
   TOADFLAX(38, PatchType.HERB),
   IRIT(44, PatchType.HERB),
   AVANTOE(50, PatchType.HERB),
   KWUARM(56, PatchType.HERB),
   SNAPDRAGON(62, PatchType.HERB),
   CADANTINE(67, PatchType.HERB),
   LANTADYME(73, PatchType.HERB),
   DWARF_WEED(79, PatchType.HERB),
   TORSTOL(85, PatchType.HERB),;

   private int level;
   private PatchType type;
   private int id;
   private int deadId;

   Seed(int level, PatchType type, int id, int deadId) {
      this.level = level;
      this.type = type;
      this.id = id;
      this.deadId = deadId;
   }

   Seed(int level, PatchType type) {
      this.level = level;
      this.type = type;
   }

   public int getLevel() {
      return level;
   }

   public PatchType getType() {
      return type;
   }

   @Override
   public String toString() {
      return name().charAt(0) + name().toLowerCase().substring(1) + " seed";
   }
}
