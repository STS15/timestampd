package com.sts15.timestampd.mixins;

import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mixin(ChatComponent.class)
@OnlyIn(Dist.CLIENT)
public class MixinChatComponent {

    // Capture and modify the chat message when it's being added
    @ModifyVariable(
            method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
            at = @At("HEAD"),
            ordinal = 0 // Indicates we are modifying the first local variable of type Component
    )
    private Component onAddMessage(Component pChatComponent) {
        // Format the current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        String timestamp = "[" + LocalDateTime.now().format(formatter) + "] ";

        // Create a new Component with the timestamp prepended
        MutableComponent timestampedMessage = Component.literal(timestamp).append(pChatComponent);

        // Return the modified message to replace the original
        return timestampedMessage;
    }
}
