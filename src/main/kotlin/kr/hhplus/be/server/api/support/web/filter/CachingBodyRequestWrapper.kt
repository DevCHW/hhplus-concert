package kr.hhplus.be.server.api.support.web.filter

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StreamUtils
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream

class CachingBodyRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    private val cachedContent = StreamUtils.copyToByteArray(request.inputStream)
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun getInputStream(): ServletInputStream {
        return object : ServletInputStream() {
            private val cachedBodyInputStream: InputStream = ByteArrayInputStream(cachedContent)

            override fun isFinished(): Boolean {
                try {
                    return cachedBodyInputStream.available() == 0
                } catch (e: IOException) {
                    log.error(e.message, e)
                }
                return false
            }

            override fun isReady(): Boolean {
                return true
            }

            override fun setReadListener(readListener: ReadListener) {
                throw UnsupportedOperationException()
            }

            @Throws(IOException::class)
            override fun read(): Int {
                return cachedBodyInputStream.read()
            }
        }
    }
}