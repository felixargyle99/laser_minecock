package org.examplecock.laser_minecock;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.CompParticle;

public final class Laser_minecock extends JavaPlugin {

    //Надо посмотреть как тип создает функции для работы, чтобы самому можно было создать необходимую
    @Override
    public void onEnable() {
        // Plugin startup logic
    }


    public void spawnLaser(Location locationA, Location locationB) {  //Должен создать лазер и пройтись по длине. Мб не хватает команды через "try"

        getCommand("spawnlaser").setExecutor(new LaserCommandExecutor(this));

        double distance = locationA.distance(locationB);
        Vector vector = locationB.clone().subtract(locationA).toVector().normalize().multiply(0.5);
        Location laserLocation = locationA.clone();
        double increment = 0.5;

        for (double totalDistance = 1; totalDistance < distance; totalDistance += increment) {
            laserLocation.add(vector);
            laserLocation.getWorld().spawnParticle(Particle.REDSTONE, laserLocation, 1, new Particle.DustOptions(Color.YELLOW, 0.75F));
        }
    }

    public class LaserCommandExecutor implements CommandExecutor {  //Должно давать команду спавнлазер, но ее не видит(скорее всего сама функция не создана для майна)

        private final Laser_minecock plugin;

        public LaserCommandExecutor(Laser_minecock plugin) {
            this.plugin = plugin;
        }
        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (command.getName().equalsIgnoreCase("spawnlaser")) {
                if (sender instanceof Player) {
                    if (args.length == 6) {
                        World world = ((Player) sender).getWorld();
                        double x1 = Double.parseDouble(args[0]);
                        double y1 = Double.parseDouble(args[1]);
                        double z1 = Double.parseDouble(args[2]);
                        double x2 = Double.parseDouble(args[3]);
                        double y2 = Double.parseDouble(args[4]);
                        double z2 = Double.parseDouble(args[5]);

                        Location locationA = new Location(world, x1, y1, z1);
                        Location locationB = new Location(world, x2, y2, z2);

                        plugin.spawnLaser(locationA, locationB);
                        return true;
                    } else {
                        sender.sendMessage("Используй /spawnlaser");
                        return false;
                    }
                } else {
                    sender.sendMessage("Ты долбоёоуб");
                    return false;
                }
            }
            return false;
        }
    }


/*
    //Вариант типа через адскую звезду
    @Override
    public void run() {
        int length = 5;
        double particleDistance = 0.5;

        for (Player online : Bukkit.getOnlinePlayers()) {
            ItemStack hand = online.getItemInHand();

            if (hand.hasItemMeta() && hand.get"ItemMeta().getDisplayName().equals(ChatColor.WHITE + \"Laser Pointer)) {
                Location location = online.getLocation().add(0, 1, 0);

                for (double waypoint = 1; waypoint < length; waypoint += particleDistance) {
                    Vector vector = location.getDirection().multiply(waypoint);
                    location.add(vector);

                    if (location.getBlock().getType() != Material.AIR)
                        break;

                    try {
                        location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, new Particle.DustOptions(Color.YELLOW, 0.75F));

                    } catch (Throwable t) {
                        // Unsupported
                    }
                }
            }
        }
    }

*/


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
