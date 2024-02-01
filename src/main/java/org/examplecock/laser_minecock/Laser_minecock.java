package org.examplecock.laser_minecock;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.mineacademy.fo.plugin.SimplePlugin;
import org.mineacademy.fo.remain.CompParticle;

public final class Laser_minecock extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    public void spawnLaser(Location locationA, Location locationB) {
        double distance = locationA.distance(locationB);
        Vector vector = locationB.clone().subtract(locationA).toVector().normalize().multiply(0.5);
        Location laserLocation = locationA.clone();
        double increment = 0.5;

        for (double totalDistance = 1; totalDistance < distance; totalDistance += increment) {
            laserLocation.add(vector);
            CompParticle.REDSTONE.spawn(laserLocation);
        }
    }

    public void spawnLaserNearbyEntities(Location center, double radius){
        for (Entity entity : center.getWorld().getNearbyEntities(center, radius, radius, radius)) {
            if (entity instanceof LivingEntity) {
                spawnLaser(center, entity.getLocation());
            }
        }
    }

    public class LaserCommandExecutor implements CommandExecutor {

        

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            if (command.getName().equalsIgnoreCase("spawnlaser")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (args.length == 1) {
                        try {
                            double radius = Double.parseDouble(args[0]);
                            spawnLaserNearbyEntities(player.getLocation(), radius);
                            return true;
                        }   catch (NumberFormatException e) {
                            player.sendMessage("Радиус говна. Используй число");
                            return false;
                        }
                    }   else {
                        player.sendMessage("Используй: /spawnlaser <radius>");
                        return false;
                    }
                }   else {
                    sender.sendMessage("Команда может использоваться только игроком");
                    return false;
                }
            }
            return false;
        }
    }









    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
