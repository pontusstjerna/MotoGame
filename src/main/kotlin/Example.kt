class Example {

    fun run() {
        println("Hello you!")

        val myInt: Int = 2
        var myInt2: Int = 3

        printNumber("hejsan", 1337)

        if (1 == 2) {
            return
        }

        if (1 == 1) println("kakor") else return

        for (x in 0..5) {
            println(x)
        }

        println(whatAmI("karin"))
        println(whatAmI("nåt annat"))
        println(whatAmI(1))

        if (1500 !in 0..1400) {
            println("not in range")
        }

        for (i in 100 downTo 50 step 10) {
            println(i)
        }

        val items: List<String> = listOf("gosigt", "banana", "avocado", "apple", "kiwifruit")
        when {
            "orange" in items -> println("juicy")
            "apple" in items -> println("apple is fine too")
        }

        val someItems = items.filter { it.startsWith("g") }

        val myLittlePony: () -> Unit = {
            println("Är söt")
        }

        val myLittleLambda: (Int) -> Int = { x -> x + 1 }
        val myBiggerLambda: (Int) -> Int = fun(x: Int): Int {
            val y = x - 1
            return y
        }

        myLittlePony()
        println(myLittleLambda(5))
        println(myBiggerLambda(5))
    }

    fun test(arg: String): Int {
        return arg.length
    }

    fun printNumber(str: String, number: Int): Unit {
        println("$str: $number")
    }

    fun whatAmI(obj: Any): String {
        return when(obj) {
            1 -> "en etta änna"
            "karin" -> "aha"
            is String -> "du är ju en sträng!"
            else -> "vet ju inte"
        }
    }
}