function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  TypeInsnNode = Java.type("org.objectweb.asm.tree.TypeInsnNode");
  AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
  return {
    "Entity getMaxAirSupply Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.Entity",
        "methodName": "func_205010_bg",
        "methodDesc": "()I"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: getMaxAirSupply net.minecraft.entity.Entity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = methodNode.instructions.getFirst();
        var popNode = new LabelNode();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/config/BetterDivingConfig", "oxygenChanges", "()Z", false),
            new JumpInsnNode(Opcodes.IFEQ, popNode),
            new VarInsnNode(Opcodes.ALOAD, 0),
            new TypeInsnNode(Opcodes.INSTANCEOF, "net/minecraft/entity/player/PlayerEntity"),
            new JumpInsnNode(Opcodes.IFEQ, popNode),
            new VarInsnNode(Opcodes.ALOAD, 0),
            new TypeInsnNode(Opcodes.CHECKCAST, "net/minecraft/entity/player/PlayerEntity"),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/EntityHook", "getMaxAirSupply", "(Lnet/minecraft/entity/player/PlayerEntity;)I", false),
            new InsnNode(Opcodes.IRETURN),
            popNode
        ));
        
        return methodNode;
      }
    }
  }
}