function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
  LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
  FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
  return {
    "MouseHelper turnPlayer Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.MouseHelper",
        "methodName": "func_198028_a",
        "methodDesc": "()V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: turnPlayer net.minecraft.client.MouseHelper");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode1 = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/client/util/MouseSmoother", ASMAPI.mapMethod("func_199102_a"), "(DD)D");
        for (var i = methodNode.instructions.indexOf(targetNode1); i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.JUMP_INSN) {
				targetNode1 = insnNode;
				break;
			}
		}
        for (var i = methodNode.instructions.indexOf(targetNode1); i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode1 = insnNode;
				break;
			}
		}
		var popNode11 = new LabelNode();
		var popNode12 = targetNode1;
		for (var i = methodNode.instructions.indexOf(popNode12); i < methodNode.instructions.size(); i++) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.JUMP_INSN) {
				popNode12 = insnNode;
				break;
			}
		}
		for (var i = methodNode.instructions.indexOf(popNode12) + 1; i < methodNode.instructions.size(); i++) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.JUMP_INSN) {
				popNode12 = insnNode;
				break;
			}
		}
		popNode12 = popNode12.label;
		
	    methodNode.instructions.insert(targetNode1, ASMAPI.listOf(
	    	new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/MouseHelperHook", "onTurnPlayer", "()Z"),
	    	new JumpInsnNode(Opcodes.IFEQ, popNode11),
	    	
	    	new VarInsnNode(Opcodes.DLOAD, 3),
	    	new VarInsnNode(Opcodes.DLOAD, 7),
	    	new VarInsnNode(Opcodes.ALOAD, 0),
	    	new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/MouseHelper", ASMAPI.mapField("field_198048_m"), "D"),
	    	new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/MouseHelperHook", "onTurnPlayerX", "(DDD)D"),
	    	new VarInsnNode(Opcodes.DSTORE, 9),
	    	
	    	new VarInsnNode(Opcodes.DLOAD, 3),
	    	new VarInsnNode(Opcodes.DLOAD, 7),
	    	new VarInsnNode(Opcodes.ALOAD, 0),
	    	new FieldInsnNode(Opcodes.GETFIELD, "net/minecraft/client/MouseHelper", ASMAPI.mapField("field_198049_n"), "D"),
	    	new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/MouseHelperHook", "onTurnPlayerY", "(DDD)D"),
	    	new VarInsnNode(Opcodes.DSTORE, 11),
	    	
	    	new JumpInsnNode(Opcodes.GOTO, popNode12),
	    	popNode11
	    ));
        
        return methodNode;
      }
    }
  }
}