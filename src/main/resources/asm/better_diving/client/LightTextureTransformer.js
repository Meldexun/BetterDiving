function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  return {
    "LightTexture updateLightmap Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.renderer.LightTexture",
        "methodName": "func_205106_a",
        "methodDesc": "(F)V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: updateLightmap net.minecraft.client.renderer.LightTexture");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCallBefore(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/client/entity/player/ClientPlayerEntity", ASMAPI.mapMethod("func_70644_a"), "(Lnet/minecraft/potion/Effect;)Z", methodNode.instructions.size() - 1);
        targetNode = ASMAPI.findFirstInstructionAfter(methodNode, Opcodes.FSTORE, methodNode.instructions.indexOf(targetNode) + 1);
        targetNode = ASMAPI.findFirstInstructionAfter(methodNode, Opcodes.FSTORE, methodNode.instructions.indexOf(targetNode) + 1);
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/LightTextureHook", "getLightmapBrightness", "()F", false),
            new VarInsnNode(Opcodes.FSTORE, 6)
        ));
        
        return methodNode;
      }
    }
  }
}