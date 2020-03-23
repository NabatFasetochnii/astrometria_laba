import Core.Companion.PATH_UCAC2
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.io.File

class PairCalculation { //Класс нахождения общих звёзд

    fun cal(i: Int, tStars: Stars, uStars: Stars, readCats: ReadCats) {

        val z = i + 1 //Нумерация файлов начинается с 1, а массивов с 0,
        // поэтому нужно компенсировать эту разницу
        var s = "$z" //переводим номер зоны в строку и при необходимости добавляем ей нужное кол-во нулей

        if (z in 1..9) {
            s = "00$z"
        } else if (z in 10..99) {
            s = "0$z"
        }
        val stream = DataInputStream(BufferedInputStream((File("$PATH_UCAC2$s").inputStream())))
        //Открываем файловый поток для той зоны, которую хотим обработать
        readCats.readUCAC(tStars, i, uStars, stream) //Читаем нужный файл в классе для чтения и записи

        stream.close() //закрываем поток

    }
}

/*
fun twoMass2us(j: Double, h: Double): Double{
    return ((j-h+0.045)*0.98)
}*/
