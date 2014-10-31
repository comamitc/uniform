package uniform.config

object UniformConfig {

  val file: java.io.File = new java.io.File("./config/uniform.conf")
  val prop = new java.util.Properties
  
  def set(key: String, value: String) =  prop.setProperty(key, value)
 
  def get(key: String) = prop.getProperty(key)
  
  def getOr(key: String, default: String) = prop.getProperty(key, default)
    
  def load = prop.load(new java.io.FileInputStream(file))
  
  def dump = prop.store(new java.io.FileOutputStream(file), null)
  
  this.load
}