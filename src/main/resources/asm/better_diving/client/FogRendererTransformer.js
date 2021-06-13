function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
	AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  return {
    "FogRenderer Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.renderer.FogRenderer",
        "methodName": "func_228371_a_",
        "methodDesc": "(Lnet/minecraft/client/renderer/ActiveRenderInfo;FLnet/minecraft/client/world/ClientWorld;IF)V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: updateFogColor net.minecraft.client.renderer.FogRenderer");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.STATIC, "net/minecraft/util/Util", ASMAPI.mapMethod("func_211177_b"), "()J");
        var popNode = ASMAPI.findFirstMethodCallAfter(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/fluid/FluidState", ASMAPI.mapMethod("func_206884_a"), "(Lnet/minecraft/tags/ITag;)Z", methodNode.instructions.indexOf(targetNode));
        //popNode = ASMAPI.findFirstInstructionBefore(methodNode, Opcodes.GOTO, methodNode.instructions.indexOf(popNode));
        for (var i = methodNode.instructions.indexOf(popNode); i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getOpcode() == Opcodes.GOTO) {
				popNode = insnNode;
				break;
			}
		}
        //popNode = ASMAPI.findFirstInstructionBefore(methodNode, -1, methodNode.instructions.indexOf(popNode) - 1);
        //popNode = ASMAPI.findFirstInstructionBefore(methodNode, -1, methodNode.instructions.indexOf(popNode) - 1);
        //popNode = ASMAPI.findFirstInstructionBefore(methodNode, -1, methodNode.instructions.indexOf(popNode) - 1);
        for (var i = methodNode.instructions.indexOf(popNode); i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				popNode = insnNode;
				break;
			}
		}
        
        methodNode.instructions.insertBefore(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/FogRendererHook", "updateFogColor", "(Lnet/minecraft/client/renderer/ActiveRenderInfo;)V", false),
            new JumpInsnNode(Opcodes.GOTO, popNode)
        ));
        
        return methodNode;
      }
    }
  }
}