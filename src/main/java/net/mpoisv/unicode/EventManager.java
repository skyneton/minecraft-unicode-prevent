package net.mpoisv.unicode;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EventManager implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("chat.unicode.block.bypass")) return;

        if(Main.detectOverflowUnicodeLength(event.getMessage())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(Main.detectOverflowFormat(event.getMessage()));
        }
    }
}
