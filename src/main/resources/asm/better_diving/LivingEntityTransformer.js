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
    "LivingEntity Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_213352_e",
        "methodDesc": "(Lnet/minecraft/util/math/vector/Vector3d;)V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: travel net.minecraft.entity.LivingEntity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_226278_cu_"), "()D");
        targetNode = methodNode.instructions.get(methodNode.instructions.indexOf(targetNode) - 1);
        var popNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_180799_ab"), "()Z");
        for (var i = methodNode.instructions.indexOf(popNode) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				popNode = insnNode;
				break;
			}
		}
		for (var i = methodNode.instructions.indexOf(popNode) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				popNode = insnNode;
				break;
			}
		}
        
        methodNode.instructions.insertBefore(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/LivingEntityHook", "handlePlayerTravelInWater", "(Lnet/minecraft/entity/LivingEntity;)Z", false),
            new JumpInsnNode(Opcodes.IFNE, popNode)
        ));
        
        return methodNode;
      }
    }
  }
}