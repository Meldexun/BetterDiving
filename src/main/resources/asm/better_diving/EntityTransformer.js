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
  FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
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
    },
    "Entity updateFluidHeightAndDoFluidPushing Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.Entity",
        "methodName": "func_210500_b",
        "methodDesc": "(Lnet/minecraft/tags/ITag;D)Z"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: updateFluidHeightAndDoFluidPushing net.minecraft.entity.Entity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = methodNode.instructions.getFirst();
        var popNode = new LabelNode();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new VarInsnNode(Opcodes.ALOAD, 1),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/EntityHook", "canBeInFluid", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/tags/ITag;)Z", false),
            new JumpInsnNode(Opcodes.IFNE, popNode),
            new InsnNode(Opcodes.ICONST_0),
            new InsnNode(Opcodes.IRETURN),
            popNode
        ));
        
        return methodNode;
      }
    },
    "Entity updateFluidOnEyes Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.Entity",
        "methodName": "func_205012_q",
        "methodDesc": "()V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: updateFluidOnEyes net.minecraft.entity.Entity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/fluid/FluidState", ASMAPI.mapMethod("func_206884_a"), "(Lnet/minecraft/tags/ITag;)Z");
        targetNode = ASMAPI.findFirstInstructionAfter(methodNode, -1, methodNode.instructions.indexOf(targetNode) + 1);
        var popNode = new LabelNode();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new VarInsnNode(Opcodes.ALOAD, 7),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/EntityHook", "canBeInFluid", "(Lnet/minecraft/entity/Entity;Lnet/minecraft/tags/ITag;)Z", false),
            new JumpInsnNode(Opcodes.IFNE, popNode),
            new InsnNode(Opcodes.RETURN),
            popNode
        ));
        
        return methodNode;
      }
    }
  }
}