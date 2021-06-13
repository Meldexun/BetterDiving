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
    },
    "LivingEntity baseTick Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_70030_z",
        "methodDesc": "()V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: baseTick net.minecraft.entity.LivingEntity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_208600_a"), "(Lnet/minecraft/tags/ITag;)Z");
		for (var i = methodNode.instructions.indexOf(targetNode) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode = insnNode;
				break;
			}
		}
		
		var targetNode1 = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_70682_h"), "(I)I");
		var popNode1 = ASMAPI.findFirstMethodCallAfter(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_70097_a"), "(Lnet/minecraft/util/DamageSource;F)Z", methodNode.instructions.indexOf(targetNode1));
		for (var i = methodNode.instructions.indexOf(targetNode1) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode1 = insnNode;
				break;
			}
		}
		for (var i = methodNode.instructions.indexOf(targetNode1) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode1 = insnNode;
				break;
			}
		}
		popNode1 = ASMAPI.findFirstInstructionAfter(methodNode, -1, methodNode.instructions.indexOf(popNode1));
        
        var targetNode2 = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/entity/LivingEntity", ASMAPI.mapMethod("func_207300_l"), "(I)I");
		var popNode2 = targetNode2;
		for (var i = methodNode.instructions.indexOf(targetNode2) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode2 = insnNode;
				break;
			}
		}
		for (var i = methodNode.instructions.indexOf(targetNode2) - 1; i >= 0; i--) {
			var insnNode = methodNode.instructions.get(i);
			if (insnNode.getType() == AbstractInsnNode.LABEL) {
				targetNode2 = insnNode;
				break;
			}
		}
		popNode2 = ASMAPI.findFirstInstructionAfter(methodNode, -1, methodNode.instructions.indexOf(popNode2));
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new VarInsnNode(Opcodes.ALOAD, 0),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/LivingEntityHook", "handleBreathing", "(Lnet/minecraft/entity/LivingEntity;)V", false)
        ));
        
        methodNode.instructions.insert(targetNode1, ASMAPI.listOf(
            new JumpInsnNode(Opcodes.GOTO, popNode1)
        ));
        
        methodNode.instructions.insert(targetNode2, ASMAPI.listOf(
            new JumpInsnNode(Opcodes.GOTO, popNode2)
        ));
        
        return methodNode;
      }
    },
    "LivingEntity canBreatheUnderwater Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_70648_aU",
        "methodDesc": "()Z"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: canBreatheUnderwater net.minecraft.entity.LivingEntity");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = methodNode.instructions.getFirst();
        var popNode = new LabelNode();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/config/BetterDivingConfig", "oxygenChanges", "()Z", false),
            new JumpInsnNode(Opcodes.IFEQ, popNode),
            new VarInsnNode(Opcodes.ALOAD, 0),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/LivingEntityHook", "canBreatheUnderwater", "(Lnet/minecraft/entity/LivingEntity;)Z", false),
            new JumpInsnNode(Opcodes.IFEQ, popNode),
            new InsnNode(Opcodes.ICONST_1),
            new InsnNode(Opcodes.IRETURN),
            popNode
        ));
        
        return methodNode;
      }
    },
    "LivingEntity decreaseAirSupply Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_70682_h",
        "methodDesc": "(I)I"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: decreaseAirSupply net.minecraft.entity.LivingEntity");
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
            new VarInsnNode(Opcodes.ILOAD, 1),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/LivingEntityHook", "decreaseAirSupply", "(Lnet/minecraft/entity/player/PlayerEntity;I)I", false),
            new InsnNode(Opcodes.IRETURN),
            popNode
        ));
        
        return methodNode;
      }
    },
    "LivingEntity increaseAirSupply Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_207300_l",
        "methodDesc": "(I)I"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: increaseAirSupply net.minecraft.entity.LivingEntity");
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
            new VarInsnNode(Opcodes.ILOAD, 1),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/LivingEntityHook", "increaseAirSupply", "(Lnet/minecraft/entity/player/PlayerEntity;I)I", false),
            new InsnNode(Opcodes.IRETURN),
            popNode
        ));
        
        return methodNode;
      }
    }
  }
}