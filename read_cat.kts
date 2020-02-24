package laba

import java.io.File

class main(){}

class Read_Cat(const val PATH_UCAC2) {

    File(PATH_UCAC2).inputStream().use {
        var byte b[]
        it.read(b, 5, 3)
        println(b)
    }


}