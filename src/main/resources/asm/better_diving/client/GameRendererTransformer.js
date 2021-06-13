function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  return {
    "GameRenderer Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.renderer.GameRenderer",
        "methodName": "func_228378_a_",
        "methodDesc": "(FJLcom/mojang/blaze3d/matrix/MatrixStack;)V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: renderLevel net.minecraft.client.renderer.GameRenderer");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var popNode1 = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/client/renderer/LightTexture", ASMAPI.mapMethod("func_205106_a"), "(F)V");
        for (var i = methodNode.instructions.indexOf(popNode1); i < methodNode.instructions.size(); i++) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				popNode1 = insnNode;
				break;
			}
		}
		var targetNode1 = null;
		for (var i = methodNode.instructions.indexOf(popNode1) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode1 = insnNode;
				break;
			}
		}
        
        var targetNode2 = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/client/renderer/WorldRenderer", ASMAPI.mapMethod("func_228426_a_"), "(Lcom/mojang/blaze3d/matrix/MatrixStack;FJZLnet/minecraft/client/renderer/ActiveRenderInfo;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lnet/minecraft/util/math/vector/Matrix4f;)V");
        for (var i = methodNode.instructions.indexOf(targetNode2); i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode2 = insnNode;
				break;
			}
		}
        
        methodNode.instructions.insert(targetNode1, ASMAPI.listOf(
            new JumpInsnNode(Opcodes.GOTO, popNode1)
        ));
        
        methodNode.instructions.insert(targetNode2, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/renderer/GameRenderer", ASMAPI.mapField("field_78513_d"), "Lnet/minecraft/client/renderer/LightTexture;"),
            new VarInsnNode(Opcodes.FLOAD, 1),
            new MethodInsnNode(Opcodes.INVOKEVIRTUAL, "net/minecraft/client/renderer/LightTexture", ASMAPI.mapMethod("func_205106_a"), "(F)V", false)
        ));
        
        return methodNode;
      }
    }
  }
}