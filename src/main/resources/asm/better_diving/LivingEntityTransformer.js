function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  return {
    "LivingEntity Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.entity.LivingEntity",
        "methodName": "func_213352_e",
        "methodDesc": "(Lnet/minecraft/util/math/vector/Vector3d;)V"
      },
      "transformer": function(methodNode) {
        /*
        var l = methodNode.instructions;
        for (var i = 0; i < l.size(); i++) {
          var ins = l.get(i);
          if (ins.getOpcode() != -1) {
            ASMAPI.log("INFO", "{} {}", i, ins.getOpcode());
          }
        } 
        //*/
        var targetNode1 = methodNode.instructions.get(102);
        var popNode1 = methodNode.instructions.get(300);
        
        methodNode.instructions.insertBefore(targetNode1, new VarInsnNode(Opcodes.ALOAD, 0));
        methodNode.instructions.insertBefore(targetNode1, new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/event/PlayerSwimmingEventHandler", "handlePlayerTravelInWater", "(Lnet/minecraft/entity/LivingEntity;)Z", false));
        methodNode.instructions.insertBefore(targetNode1, new JumpInsnNode(Opcodes.IFNE, popNode1));
        
        return methodNode;
      }
    }
  }
}