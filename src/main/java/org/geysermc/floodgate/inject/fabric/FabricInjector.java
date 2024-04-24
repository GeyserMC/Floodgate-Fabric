package org.geysermc.floodgate.inject.fabric;

import io.netty.channel.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.geysermc.floodgate.inject.CommonPlatformInjector;

@RequiredArgsConstructor
public final class FabricInjector extends CommonPlatformInjector {
    private static FabricInjector instance;

    @Getter private final boolean injected = true;

    @Override
    public void inject() throws Exception {
        //no-op
    }

    public void injectClient(ChannelFuture future) {
        future.channel().pipeline().addFirst("floodgate-init", new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                super.channelRead(ctx, msg);

                Channel channel = (Channel) msg;
                channel.pipeline().addLast(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        injectAddonsCall(channel, false);
                        addInjectedClient(channel);
                        channel.closeFuture().addListener(listener -> {
                            channelClosedCall(channel);
                            removeInjectedClient(channel);
                        });
                    }
                });
            }
        });
    }

    @Override
    public void removeInjection() throws Exception {
        //no-op
    }

    public static FabricInjector getInstance() {
        return instance;
    }

    public static void setInstance(FabricInjector injector) {
        instance = injector;
    }
}
