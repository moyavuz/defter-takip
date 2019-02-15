import { Moment } from 'moment';
import { IIscilik } from 'app/shared/model/iscilik.model';
import { IProjeTuru } from 'app/shared/model/proje-turu.model';
import { IBolge } from 'app/shared/model/bolge.model';

export const enum GorevDurumu {
    BEKLIYOR = 'BEKLIYOR',
    HAZIR = 'HAZIR',
    CALISILIYOR = 'CALISILIYOR',
    TAMAMLANDI = 'TAMAMLANDI'
}

export const enum OnemDurumu {
    ACIL = 'ACIL',
    IVEDI = 'IVEDI',
    NORMAL = 'NORMAL'
}

export interface IProje {
    id?: number;
    ad?: string;
    aciklama?: string;
    detay?: any;
    dosyaContentType?: string;
    dosya?: any;
    durumu?: GorevDurumu;
    onemDurumu?: OnemDurumu;
    tarih?: Moment;
    baslamaTarihi?: Moment;
    bitisTarihi?: Moment;
    isciliks?: IIscilik[];
    turu?: IProjeTuru;
    bolge?: IBolge;
}

export class Proje implements IProje {
    constructor(
        public id?: number,
        public ad?: string,
        public aciklama?: string,
        public detay?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public durumu?: GorevDurumu,
        public onemDurumu?: OnemDurumu,
        public tarih?: Moment,
        public baslamaTarihi?: Moment,
        public bitisTarihi?: Moment,
        public isciliks?: IIscilik[],
        public turu?: IProjeTuru,
        public bolge?: IBolge
    ) {}
}