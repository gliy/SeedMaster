package com.seed.gui;

import com.seed.PatchType;
import com.seed.Seed;
import com.seed.SeedSettings;
import com.seed.SeedUtils;
import org.tbot.util.Filter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Ian on 6/7/2015.
 */
public class SeedGUI extends JFrame {
   private final JComponent tabs;
   private final JComponent opts;
   private final JComponent finish;
   private java.util.List<SeedSelectionGUI> selectionGUIs;

   public SeedGUI (SeedSettings seedSettings) {
      super("Seed Master");
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      this.selectionGUIs = new ArrayList<>();
      setLocation(1500, 100);
      getContentPane().setLayout(new BorderLayout());
      tabs = setupSeedTabs(seedSettings);
      getContentPane().add(tabs, BorderLayout.NORTH);
      opts = setupOptions(seedSettings);
      getContentPane().add(opts, BorderLayout.CENTER);
      finish = setupFinish(seedSettings);
      getContentPane().add(finish, BorderLayout.SOUTH);
      pack();
      setResizable(false);
      setVisible(true);

   }

   private JComponent setupFinish (SeedSettings seedSettings) {
      JPanel done = new JPanel();
      done.setLayout(new BorderLayout());
      JPanel buttons = new JPanel();
      JButton cancel = new JButton("Cancel");
      buttons.add(cancel);
      cancel.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed (ActionEvent e) {
            SeedGUI.this.dispose();
            
         }
      });
      JButton start = new JButton("Start");
      buttons.add(start);
      done.add(buttons, BorderLayout.EAST);
      return done;

   }


   private JComponent setupOptions (SeedSettings seedSettings) {
     // JCheckBox superCompost = new JCheckBox("Use super compost");

      JPanel options = new JPanel();
      options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));
      options.setBorder(BorderFactory.createTitledBorder("Options"));

      JPanel miscOpt = new JPanel();
   //   miscOpt.add(superCompost);
      options.add(setupLocations());
      options.add(miscOpt);
      return options;
   }

   private Component setupLocations () {
      JPanel options = new JPanel();
      options.setLayout(new GridLayout(2,2));
      options.setBorder(BorderFactory.createTitledBorder("Patches"));

      for(int i = 0 ; i < 4;i++) {
         options.add(new JCheckBox("Checkbox " +1));
      }
      return options;
   }

   private JComponent setupSeedTabs (SeedSettings seedSettings) {
      final JPanel seeds = new JPanel();

      seeds.setLayout(new BoxLayout(seeds,BoxLayout.X_AXIS));

      int level = 40;///...
      for (PatchType type : PatchType.values()) {
         seeds.add(setupSeedTab(type, level));
      }
      seeds.setBorder(BorderFactory.createTitledBorder("Seeds"));
      return seeds;

   }
   private JPanel setupSeedTab(final PatchType type, final int level) {
      List<Seed> seeds = SeedUtils.filter(Seed.values(), new Filter<Seed>() {
         @Override
         public boolean accept (Seed seed) {
            return seed.getLevel() <= level && seed.getType() == type;
         }
      });
      JPanel rtn = new JPanel();
      rtn.setBorder(BorderFactory.createTitledBorder(
              BorderFactory.createLineBorder(Color.black, 1),type.toString()));
      if(seeds.isEmpty()) {
         rtn.add(new JLabel("No seeds for your level"));
      }else {
         JComboBox<Seed> comp = new JComboBox<>(new SeedSelectionGUI(seeds));
         rtn.add(comp);
      }
      return rtn;
   }

   private static class SeedSelectionGUI extends AbstractListModel
           implements ComboBoxModel {
      private final Comparator<? super Seed> seedSorter = new Comparator<Seed>() {
         @Override
         public int compare (Seed o1, Seed o2) {
            return new Integer(o1.getLevel()).compareTo(o2.getLevel());
         }
      };
      private List<Seed> seeds;
      private Seed current = null;
      public SeedSelectionGUI (List<Seed> seeds) {
         this.seeds = new ArrayList<>(seeds);
         Collections.sort(this.seeds, seedSorter);
         setSelectedItem(this.seeds.get(seeds.size()-1));

      }

      @Override
      public void setSelectedItem (Object anItem) {
         current = (Seed)anItem;
      }

      @Override
      public Object getSelectedItem () {
         return  current;//seeds.get(this.);
      }

      @Override
      public int getSize () {
         return seeds.size();
      }

      @Override
      public Object getElementAt (int index) {
         return seeds.get(index);
      }

   }

   public static void main (String[] args) throws Throwable {
      SeedGUI seedGUI = new SeedGUI(null);
      seedGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }

}
