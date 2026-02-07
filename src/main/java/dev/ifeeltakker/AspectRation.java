package dev.ifeeltakker;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;

public class AspectRation implements ClientModInitializer {

    public static boolean enabled = false;
    public static float aspectRatio = 16f / 9f;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    literal("aspectratio")
                            .then(literal("toggle")
                                    .executes(ctx -> {
                                        enabled = !enabled;
                                        ctx.getSource().sendFeedback(
                                                Text.literal("AspectRatio: " + (enabled ? "§aON" : "§cOFF"))
                                        );
                                        return 1;
                                    })
                            )
                            .then(literal("change")
                                    .then(argument("width", IntegerArgumentType.integer(1))
                                            .then(argument("height", IntegerArgumentType.integer(1))
                                                    .executes(ctx -> {
                                                        int width = IntegerArgumentType.getInteger(ctx, "width");
                                                        int height = IntegerArgumentType.getInteger(ctx, "height");

                                                        aspectRatio = (float) width / (float) height;

                                                        ctx.getSource().sendFeedback(
                                                                Text.literal("AspectRatio set to " + width + ":" + height)
                                                        );
                                                        return 1;
                                                    })
                                            )
                                    )
                            )
            );
        });
    }
}