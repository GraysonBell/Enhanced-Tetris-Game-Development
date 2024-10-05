package model;

public final class OpMove extends Record {
    private final int opX;

    private final int opRotate;

    public OpMove(int opX, int opRotate) {
        this.opX = opX;
        this.opRotate = opRotate;
    }

    public final String toString() {
        // Byte code:
        //   0: aload_0
        //   1: <illegal opcode> toString : (Lmodel/OpMove;)Ljava/lang/String;
        //   6: areturn
        // Line number table:
        //   Java source line number -> byte code offset
        //   #3	-> 0
        // Local variable table:
        //   start	length	slot	name	descriptor
        //   0	7	0	this	Lmodel/OpMove;
    }

    public final int hashCode() {
        // Byte code:
        //   0: aload_0
        //   1: <illegal opcode> hashCode : (Lmodel/OpMove;)I
        //   6: ireturn
        // Line number table:
        //   Java source line number -> byte code offset
        //   #3	-> 0
        // Local variable table:
        //   start	length	slot	name	descriptor
        //   0	7	0	this	Lmodel/OpMove;
    }

    public final boolean equals(Object o) {
        // Byte code:
        //   0: aload_0
        //   1: aload_1
        //   2: <illegal opcode> equals : (Lmodel/OpMove;Ljava/lang/Object;)Z
        //   7: ireturn
        // Line number table:
        //   Java source line number -> byte code offset
        //   #3	-> 0
        // Local variable table:
        //   start	length	slot	name	descriptor
        //   0	8	0	this	Lmodel/OpMove;
        //   0	8	1	o	Ljava/lang/Object;
    }

    public int opX() {
        return this.opX;
    }

    public int opRotate() {
        return this.opRotate;
    }
}
