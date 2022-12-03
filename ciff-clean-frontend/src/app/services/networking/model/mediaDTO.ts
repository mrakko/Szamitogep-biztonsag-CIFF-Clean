/**
 * Ciff Clean - OpenAPI 3.0
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 0.9.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import { CommentDTO } from './commentDTO';
import { PublicUserDTO } from './publicUserDTO';

export interface MediaDTO { 
    fileId?: number;
    fileName?: string;
    uploadDate?: Date;
    uploader?: PublicUserDTO;
    comments?: Array<CommentDTO>;
}