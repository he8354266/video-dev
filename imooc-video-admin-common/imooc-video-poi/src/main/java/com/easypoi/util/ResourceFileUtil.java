package com.easypoi.util;/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2021/2/2310:23
 */

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author zkjyCoding
 * @version 1.0
 * @description zkjy
 * @updateRemark
 * @updateUser
 * @createDate 2021/2/23 10:23
 * @updateDate 2021/2/23 10:23
 **/
public class ResourceFileUtil {
    /**
     * 获取资源文件
     *
     * @param relativePath 资源文件相对路径(相对于 resources路径,路径 + 文件名)
     *                     eg: "templates/pdf_export_demo.ftl"
     * @return
     * @throws FileNotFoundException
     */
    public static File getFile(String relativePath) throws FileNotFoundException {
        if (relativePath == null || relativePath.length() == 0) {
            return null;
        }
        if (relativePath.startsWith("/")) {
            relativePath = relativePath.substring(1);
        }
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX
                + relativePath);

        return file;
    }

    /**
     * 获取资源绝对路径
     *
     * @param relativePath 资源文件相对路径(相对于 resources路径,路径 + 文件名)
     *                     eg: "templates/pdf_export_demo.ftl"
     * @return
     * @throws FileNotFoundException
     */
    public static String getAbsolutePath(String relativePath) throws FileNotFoundException {
        return getFile(relativePath).getAbsolutePath();
    }

    /**
     * 获取资源父级目录
     *
     * @param relativePath 资源文件相对路径(相对于 resources路径,路径 + 文件名)
     *                     eg: "templates/pdf_export_demo.ftl"
     * @return
     * @throws FileNotFoundException
     */
    public static String getParent(String relativePath) throws FileNotFoundException {
        return getFile(relativePath).getParent();
    }

    /**
     * 获取资源文件名
     *
     * @param relativePath 资源文件相对路径(相对于 resources路径,路径 + 文件名)
     *                     eg: "templates/pdf_export_demo.ftl"
     * @return
     * @throws FileNotFoundException
     */
    public static String getFileName(String relativePath) throws FileNotFoundException {
        return getFile(relativePath).getName();
    }
}
