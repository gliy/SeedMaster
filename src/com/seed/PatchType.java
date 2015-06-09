package com.seed;

/**
 * Created by Ian on 5/26/2015.
 */
public enum PatchType {
   ALLOTMENT(8573), HERB(8132), FLOWER(7840),;

   int id;

   PatchType(int id) {
      this.id = id;
   }

   public int getId() {
      return id;
   }


   public static PatchType typeFromId(int id) {
      for (PatchType type : values()) {
         if (type.getId() == id) {
            return type;
         }
      }
      return null;
   }

   @Override
   public String toString () {
      return name().charAt(0) + name().substring(1).toLowerCase();
   }
}
