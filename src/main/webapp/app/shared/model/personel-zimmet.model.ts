import { Moment } from 'moment';
import { IPersonel } from 'app/shared/model/personel.model';
import { IZimmetTuru } from 'app/shared/model/zimmet-turu.model';

export const enum ZimmetDurumu {
    YENI = 'YENI',
    KULLANILMIS = 'KULLANILMIS'
}

export interface IPersonelZimmet {
    id?: number;
    tarih?: Moment;
    durumu?: ZimmetDurumu;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
    personel?: IPersonel;
    zimmet?: IZimmetTuru;
}

export class PersonelZimmet implements IPersonelZimmet {
    constructor(
        public id?: number,
        public tarih?: Moment,
        public durumu?: ZimmetDurumu,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public personel?: IPersonel,
        public zimmet?: IZimmetTuru
    ) {}
}
