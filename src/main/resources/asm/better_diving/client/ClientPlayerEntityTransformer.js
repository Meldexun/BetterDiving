function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  return {
    "ClientPlayerEntity getWaterBrightness Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.entity.player.ClientPlayerEntity",
        "methodName": "func_203719_J",
        "methodDesc": "()F"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: getWaterBrightness net.minecraft.client.entity.player.ClientPlayerEntity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = methodNode.instructions.getFirst();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/ClientPlayerEntityHook", "getWaterBrightness", "(Lnet/minecraft/client/entity/player/ClientPlayerEntity;)F", false),
            new InsnNode(Opcodes.FRETURN)
        ));
        
        return methodNode;
      }
    }
  }
}