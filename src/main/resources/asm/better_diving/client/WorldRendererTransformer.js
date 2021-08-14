function initializeCoreMod() {
  ASMAPI = Java.type("net.minecraftforge.coremod.api.ASMAPI");
  AbstractInsnNode = Java.type("org.objectweb.asm.tree.AbstractInsnNode");
  Opcodes = Java.type("org.objectweb.asm.Opcodes");
  VarInsnNode = Java.type("org.objectweb.asm.tree.VarInsnNode");
  InsnNode = Java.type("org.objectweb.asm.tree.InsnNode");
  FieldInsnNode = Java.type("org.objectweb.asm.tree.FieldInsnNode");
  MethodInsnNode = Java.type("org.objectweb.asm.tree.MethodInsnNode");
  JumpInsnNode = Java.type("org.objectweb.asm.tree.JumpInsnNode");
  LabelNode = Java.type("org.objectweb.asm.tree.LabelNode");
  return {
    "WorldRenderer renderSky Transformer": {
      "target": {
        "type": "METHOD",
        "class": "net.minecraft.client.renderer.WorldRenderer",
        "methodName": "func_228424_a_",
        "methodDesc": "(Lcom/mojang/blaze3d/matrix/MatrixStack;F)V"
      },
      "transformer": function(methodNode) {
        ASMAPI.log("INFO", "Transforming method: renderSky net.minecraft.client.renderer.WorldRenderer");
        //ASMAPI.log("INFO", "{}", ASMAPI.methodNodeToString(methodNode));
        
        var targetNode = ASMAPI.findFirstMethodCall(methodNode, ASMAPI.MethodType.VIRTUAL, "net/minecraft/client/renderer/vertex/VertexFormat", ASMAPI.mapMethod("func_227895_d_"), "()V");
        for (var i = methodNode.instructions.indexOf(targetNode); i < methodNode.instructions.size(); i++) {
          var insnNode = methodNode.instructions.get(i);
          if (insnNode.getType() == AbstractInsnNode.LABEL) {
            targetNode = insnNode;
            break;
          }
        }
        var popNode = new LabelNode();
        
        methodNode.instructions.insert(targetNode, ASMAPI.listOf(
            new MethodInsnNode(Opcodes.INVOKESTATIC, "meldexun/better_diving/plugin/client/WorldRendererHook", "shouldRenderSky", "()Z", false),
            new JumpInsnNode(Opcodes.IFNE, popNode),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem", ASMAPI.mapMethod("enableTexture"), "()V", false),
            new InsnNode(Opcodes.ICONST_1),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem", ASMAPI.mapMethod("depthMask"), "(Z)V", false),
            new MethodInsnNode(Opcodes.INVOKESTATIC, "com/mojang/blaze3d/systems/RenderSystem", ASMAPI.mapMethod("disableFog"), "()V", false),
            new InsnNode(Opcodes.RETURN),
            popNode
        ));
        
        return methodNode;
      }
    }
  }
}