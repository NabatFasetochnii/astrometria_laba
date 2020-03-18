import Core.Companion.dA
import Core.Companion.dD

class DeltaCalc {

    fun bigSysDelta() {
        var deltaA = 0.0
        var deltaD = 0.0

        for (i in dA) {
            deltaA += i / dA.size
        }

        for (i in dA) {
            deltaD += i / dD.size
        }

        for (i in 0 until dA.size) {
            dA[i] -= deltaA
        }

        for (i in 0 until dD.size) {
            dD[i] -= deltaD
        }

    }

}