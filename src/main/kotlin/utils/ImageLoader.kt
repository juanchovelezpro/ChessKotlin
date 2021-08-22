package utils

import java.awt.Image
import javax.swing.ImageIcon

class ImageLoader {
    companion object {
        fun loadImage(name: String): Image {
            val loader = ImageLoader::class.java.classLoader
            val resource = loader.getResource(name)
            val icon = ImageIcon(resource)
            return icon.image
        }
    }
}