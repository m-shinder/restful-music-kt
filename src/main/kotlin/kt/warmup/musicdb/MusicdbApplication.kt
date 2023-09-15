package kt.warmup.musicdb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MusicdbApplication

fun main(args: Array<String>) {
	runApplication<MusicdbApplication>(*args)
}
