package com.seed.gui;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 6/8/2015.
 */
public class Binder {
   private Map<String, JComponent> bindings;

   private static Binder binder;

   private Binder () {
      this.bindings = new HashMap<>();
   }


   public static Binder getBinder () {
      return binder == null ? (binder = new Binder()) : binder;
   }

   public Binder bind (String name, JComponent comp) {
      bindings.put(name, comp);
      return this;

   }

   public <E> E create (Class<E> h) {
      return null;
   }

   private abstract class GuiBinder<E> {
      private Class<? extends JComponent> clz;

      public GuiBinder (Class<? extends JComponent> clz) {
         this.clz = clz;
      }

      public Class<? extends JComponent> getBinding () {
         return this.clz;
      }

      public abstract <E> E getValue ();
   }

   private static class JCheckBoxBinder {

   }
}

